package com.readme.rss.controller;

import static java.lang.Thread.sleep;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.ProjectEntity;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.readme.rss.data.service.FrameworkService;
import com.readme.rss.data.service.ProjectService;
import com.readme.rss.data.service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3005")
@RestController
public class UnzipController {
    private ProjectService projectService;
    private UserService userService;
    private FrameworkService frameworkService;

    @Autowired
    public UnzipController(ProjectService projectService, UserService userService, FrameworkService frameworkService) {
        this.projectService = projectService;
        this.userService = userService;
        this.frameworkService = frameworkService;
    }

    // global variable
    static List<String> randomIdList = new ArrayList<>();
    static List<String> file_pathList = new ArrayList<>();
    static List<String> file_nameList = new ArrayList<>();
    static List<String> file_contentList = new ArrayList<>();

    public static String projectIdGenerate(){ // random한 projectId 생성하는 함수
        int tempRandomId = 0;
        int min = 100000, max = 999999;
        Random random = new Random();
        random.setSeed(System.nanoTime());

        for(int i = 0 ; ; i++){
            tempRandomId = random.nextInt((max - min) + min);
            if(randomIdList.indexOf(tempRandomId) == -1){ // idList에 없는 랜덤 id가 결정되면
                randomIdList.add(String.valueOf(tempRandomId));
                break;
            }
        }
        System.out.println("randomIdList: " + randomIdList);
        String randomId = Integer.toString(tempRandomId);

        return randomId;
    }

    public static String findSpringBootVersion(String xmlContent) {
        String springBootVersion = "";

        if (xmlContent.contains("<parent>")) {
            /* 정규 표현식을 통해 특정 단어 사이의 단어 추출 가능
                '/b' : 단어의 경계를 의미
                () 괄호 묶음 : 하나의 그룹을 의미 -> 두 번째 그룹이 원하는 파싱 값이므로 group(2)를 trim()
                (?<=\<parent>) : <parent>를 기준으로 그 뒤 문자열 탐색
                (.*?) : 최소 패턴 일치, 뒤에 오는 문자열을 만날 때까지
                (?=<\/parent>) : </parent>를 기준으로 그 앞 문자열 탐색
                .는 개행 문자는 포함하지 않기 때문에 주의!!
            */
            Pattern pattern = Pattern.compile("(?<=\\<parent>)(.*?)(?=<\\/parent>)");
            Matcher matcher = pattern.matcher(xmlContent);
            if (matcher.find()) {
                springBootVersion = matcher.group();
            }
            pattern = Pattern.compile("(?<=\\<version>)(.*?)(?=<\\/version>)");
            matcher = pattern.matcher(springBootVersion);
            if (matcher.find()) {
                springBootVersion = matcher.group();
            }
        }

        return springBootVersion;
    }

    public static List<String> findPackageName(String xmlContent) {
        List<String> packageName = new ArrayList<>();
        String tempStr = "";

        // <dependencies></dependencies> 안에 있는 groupId 제거하기 위한 작업
        Pattern pattern = Pattern.compile("(?<=\\<dependencies>)(.*?)(?=<\\/dependencies>)");
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            tempStr = matcher.group();
        }
        String tempXmlContent = xmlContent.replaceAll(tempStr, "");

        // <parent></parent> 안에 있는 groupId 제거하기 위한 작업
        pattern = Pattern.compile("(?<=\\<parent>)(.*?)(?=<\\/parent>)");
        matcher = pattern.matcher(tempXmlContent);
        if (matcher.find()) {
            tempStr = matcher.group();
        }
        tempXmlContent = tempXmlContent.replaceAll(tempStr, "");

        // <build></build> 안에 있는 groupId 제거하기 위한 작업
        pattern = Pattern.compile("(?<=\\<build>)(.*?)(?=<\\/build>)");
        matcher = pattern.matcher(tempXmlContent);
        if (matcher.find()) {
            tempStr = matcher.group();
        }
        tempXmlContent = tempXmlContent.replaceAll(tempStr, "");

        // =================== package명 구하기 ===================
        String groupId = "";
        String name = "";

        // groupId 구하기
        pattern = Pattern.compile("(?<=\\<groupId>)(.*?)(?=<\\/groupId>)");
        matcher = pattern.matcher(tempXmlContent);
        if (matcher.find()) {
            groupId = matcher.group();
        }
        // name 구하기
        pattern = Pattern.compile("(?<=\\<name>)(.*?)(?=<\\/name>)");
        matcher = pattern.matcher(tempXmlContent);
        if (matcher.find()) {
            name = matcher.group();
        }

        packageName.add(groupId);
        packageName.add(name);

        return packageName;
    }

    public static String findJavaVersion(String xmlContent) {
        String javaVersion = "";
        Pattern pattern = Pattern.compile("(?<=\\<java.version>)(.*?)(?=<\\/java.version>)");
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            javaVersion = matcher.group();
        }

        return javaVersion;
    }

    // dependencies 구하는 함수
    public static List<Object> findDependencies(String xmlContent) {
        String dependencies = "";
        String tempStr = "";
        Pattern pattern = Pattern.compile("(<dependencies>)(.*?)(</dependencies>)");
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            tempStr = matcher.group();
            tempStr = tempStr.replaceAll("\\s+", ""); // 연속된 공백 제거
        }

        tempStr = tempStr.replaceAll("<dependency>", "\n    <dependency>\n        ");
        tempStr = tempStr.replaceAll("<artifactId>", "\n        <artifactId>");
        tempStr = tempStr.replaceAll("<optional>", "\n        <optional>");
        tempStr = tempStr.replaceAll("<scope>", "\n        <scope>");
        tempStr = tempStr.replaceAll("<version>", "\n        <version>");
        tempStr = tempStr.replaceAll("</dependency>", "\n    </dependency>");
        tempStr = tempStr.replaceAll("</dependencies>", "\n</dependencies>");

        dependencies = "```bash\n" + tempStr + "\n```";

        List<String> dependencyContents = new ArrayList<>();
        pattern = Pattern.compile("(?<=\\<dependency>)(.*?)(?=<\\/dependency>)");
        matcher = pattern.matcher(xmlContent);
        while (matcher.find()) {
            dependencyContents.add(matcher.group());
        }

        List<String> dependencyName = new ArrayList<>();
        String artifactId = "";
        for(int i = 0 ; i < dependencyContents.size() ; i++){
            Pattern pattern2 = Pattern.compile("(?<=\\<artifactId>)(.*?)(?=<\\/artifactId>)");
            Matcher matcher2 = pattern2.matcher(dependencyContents.get(i));
            if(dependencyContents.get(i).contains("<version>")){
                if(matcher2.find()){
                    String tempArtifactId = matcher2.group();
                    Pattern pattern3 = Pattern.compile("(?<=\\<version>)(.*?)(?=<\\/version>)");
                    Matcher matcher3 = pattern3.matcher(dependencyContents.get(i));
                    if(matcher3.find()){
                        String version = matcher3.group();
                        artifactId = tempArtifactId + " (version: " + version + ")";
                    }
                }
            } else{
                if(matcher2.find()){
                    artifactId = matcher2.group();
                }
            }
            dependencyName.add(artifactId);
        }
        List<Object> retDependency = new ArrayList<>();
        retDependency.add(dependencyName);
        retDependency.add(dependencies);

        return retDependency;
    }

    public static String findDatabaseName(String propertiesContent) {
        String databaseName = "";

        // Pattern pattern = Pattern.compile("(jdbc:)(.*?)(://)"); // 전후 문자열 포함한 문자열 추출
        Pattern pattern = Pattern.compile("(\\bjdbc:\\b)(.*?)(\\b://\\b)");
        Matcher matcher = pattern.matcher(propertiesContent);
        if (matcher.find()) {
            databaseName = matcher.group(2).trim();
        }

        return databaseName;
    }

    @PostMapping(value = "/register")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String jsonParam1,
        @RequestParam("jsonParam2") String jsonParam2, @RequestParam("file") MultipartFile file)
        throws IOException, InterruptedException {
        HashMap<String,Object> map = new HashMap<String,Object>();

        String fileName = file.getOriginalFilename();
        String userName = jsonParam1;
        String repositoryName = jsonParam2;

        if(fileName == ""){ // zip 파일이 첨부되지 않았을 때
            System.out.println("\nzip 파일이 첨부되지 않았습니다!");
        }

        while(true) { // 나중에 로딩 페이지로
            sleep(1);
            if (fileName != "" && userName != "" && repositoryName != "") { // zip 파일이 입력되면
                break;
            }
        }
        System.out.println("\n입력받은 zip 파일 명 : " + fileName);

        // project table에서 id 가져오기
        randomIdList = projectService.getIdAll();
        String randomId = projectIdGenerate();
        String unzipFilePathName = "./unzipTest_" + randomId + ".zip";
        System.out.println("unzipFilePathName : " + unzipFilePathName);
        Path savePath = Paths.get("./unzipTest.zip"); // unzipTest.zip이름으로 저장
        file.transferTo(savePath); // 파일 다운로드

        ProcessBuilder builder = new ProcessBuilder();

        // unzipFiles 폴더 생성 - 압축풀기한 파일들을 저장하는 임시 폴더
        builder.command("mkdir", "unzipFiles"); // mac
        // builder.command("cmd.exe","/c","mkdir", "unzipFiles"); // window
        builder.start();

        // 파일 압축 풀기
        builder.command("unzip", "unzipTest.zip", "-d", "./unzipFiles"); // mac
        // builder.command("cmd.exe","/c","unzip", "unzipTest.zip", "-d", "./unzipFiles"); // window
        var process = builder.start(); // upzip 실행

        // unzip 실행 후, 콘솔에 출력해주기
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                System.out.println(commandResult);
            }
        }

        // project architecture
        // tree 명령어
        builder.directory(new File("./unzipFiles")); // 현재 위치 이동
        builder.start();
        builder.command("tree"); // mac
        // builder.command("cmd.exe","/c","tree"); // window
        process = builder.start();

        String architecture = "\n<!-- Project Architecture -->\n";
        architecture += "```bash\n";

        // tree 명령어 실행 후, 콘솔에 출력해주기
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                architecture += commandResult + "   \n";
            }
        }
        architecture += "```\n";

        builder.directory(new File("../backend")); // 원래 위치로 이동
        builder.start();

        // 압축 푼 파일들 중에서 원하는 정보 찾기(ex. url 찾기)
        String searchDirPath = "./unzipFiles";
        System.out.println("\n[압축 해제한 폴더 속 파일 리스트]");
        int retSearchFiles = 0; // 파일 리스트 다 뽑아냈는지 확인할 수 있는 리턴값
        retSearchFiles = searchFiles(searchDirPath);

        //------------- db insert 관련 -------------//
        List<String> javaFileName = new ArrayList<>();
        List<String> javaFilePath = new ArrayList<>();
        List<String> javaFileContent = new ArrayList<>();
        List<String> javaFileDetail = new ArrayList<>();

        for(int i = 0 ; i < file_nameList.size() ; i++){
            if((file_nameList.get(i).contains("pom.xml")) ||
                (file_nameList.get(i).contains(".java") && file_pathList.get(i).contains("src/main/java/")) ||
                (file_pathList.get(i).contains("src/main/resources/application.properties"))){

                javaFileName.add(file_nameList.get(i));
                javaFilePath.add(file_pathList.get(i));
                javaFileContent.add(file_contentList.get(i));

                if((file_nameList.get(i).contains("pom.xml")) ||
                    (file_pathList.get(i).contains("src/main/resources/application.properties"))){
                    javaFileDetail.add("etc"); // 기타
                } else{ // java 파일
                    if(file_contentList.get(i).contains("@RestController")){
                        javaFileDetail.add("controller");
                    } else if(file_contentList.get(i).contains("implements")) {
                        javaFileDetail.add("Impl");
                    } else{ // class
                        javaFileDetail.add("noImpl");
                    }
                }
            }
        }
        if(retSearchFiles == 1){ // 파일 리스트 다 뽑아냈으면 전역변수 초기화
            file_nameList.clear();
            file_pathList.clear();
            file_contentList.clear();
        }

        // project architecture project table에 insert
        projectService.saveProject(randomId, "Project Architecture", "", architecture, "tree");

        // project table에 .java 파일만 insert
        for(int i = 0 ; i < javaFileName.size() ; i++){
            try{
                projectService.saveProject(randomId, javaFileName.get(i), javaFilePath.get(i), javaFileContent.get(i), javaFileDetail.get(i));
            } catch (Exception e){
                System.out.print(javaFileName.get(i)); // 어느 파일이 길이가 긴지 확인
            }
        }

        // user table에 insert
        userService.saveUser(randomId, userName, repositoryName);

        // content data 보냈으므로, 압축풀기한 파일들, 업로드된 zip 파일 모두 삭제
        deleteUnzipFiles(builder);

        // =============== pom.xml에서 필요한 데이터 파싱 =============== //
        String xmlPath = ""; // test
        String xmlContent = "";
        // =============== application.properties에서 필요한 데이터 파싱 =============== //
        String propertiesPath = ""; // test
        String propertiesContent = "";
        // =============== 디렉토리별 파일 구분 =============== //
        List<String> controllerDir = new ArrayList<>();
        List<String> dtoDir = new ArrayList<>();
        List<String> repositoryDir = new ArrayList<>();
        List<String> daoDir = new ArrayList<>();
        List<String> daoImplDir = new ArrayList<>();
        List<String> entityDir = new ArrayList<>();
        List<String> entityImplDir = new ArrayList<>();
        List<String> handlerDir = new ArrayList<>();
        List<String> handlerImplDir = new ArrayList<>();
        List<String> serviceDir = new ArrayList<>();
        List<String> serviceImplDir = new ArrayList<>();
        List<String> etcDir = new ArrayList<>();

        List<ProjectEntity> getProjectTableRow = projectService.getFileContent(randomId);
        for(int i = 0 ; i < getProjectTableRow.size() ; i++){
            if(getProjectTableRow.get(i).getFile_path().contains("pom.xml")){
                xmlPath = getProjectTableRow.get(i).getFile_path();
                xmlContent = getProjectTableRow.get(i).getFile_content();
            } else if(getProjectTableRow.get(i).getFile_path().contains("application.properties")){
                propertiesPath = getProjectTableRow.get(i).getFile_path(); // test
                propertiesContent = getProjectTableRow.get(i).getFile_content();
            }

            // 주요 자바 파일들 디렉토리별로 구분하기
            if(getProjectTableRow.get(i).getFile_path().contains("CONTROLLER".toLowerCase())){
                controllerDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("DTO".toLowerCase())){
                dtoDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("REPOSITORY".toLowerCase())){
                repositoryDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("DAO".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    daoImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    daoDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("ENTITY".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    entityImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    entityDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("HANDLER".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    handlerImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    handlerDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("SERVICE".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    serviceImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    serviceDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else{
                etcDir.add(getProjectTableRow.get(i).getFile_name());
            }
        }

        /* test
        System.out.println("controllerDir : " + controllerDir);
        System.out.println("dtoDir : " + dtoDir);
        System.out.println("repositoryDir : " + repositoryDir);
        System.out.println("daoDir : " + daoDir);
        System.out.println("daoImplDir : " + daoImplDir);
        System.out.println("entityDir : " + entityDir);
        System.out.println("entityImplDir : " + entityImplDir);
        System.out.println("handlerDir : " + handlerDir);
        System.out.println("handlerImplDir : " + handlerImplDir);
        System.out.println("serviceDir : " + serviceDir);
        System.out.println("serviceImplDir : " + serviceImplDir);
        System.out.println("etcDir : " + etcDir);
         */

        // 공백 제거한 xmlContent - 정규식을 쓰기 위해 줄바꿈 제거
        String noWhiteSpaceXml = xmlContent.replaceAll("\n", "");

        // 필요한 데이터 : 스프링부트 버전, 패키지명, 자바 jdk 버전, (+ dependency 종류)
        String springBootVersion = findSpringBootVersion(noWhiteSpaceXml);
        List<String> packageName = findPackageName(noWhiteSpaceXml);
        String groupId = packageName.get(0);
        String artifactId = packageName.get(1);
        String javaVersion = findJavaVersion(noWhiteSpaceXml);
        // String dependencies = findDependencies(noWhiteSpaceXml); // 미완

        // 공백 제거한 propertiesContent - 정규식을 쓰기 위해 줄바꿈 제거
        String noWhiteSpaceProperties = propertiesContent.replaceAll("\n", "");

        // 필요한 데이터 : 사용하는 database명
        String databaseName = findDatabaseName(noWhiteSpaceProperties);

        //----------- db select in framework table -----------//
        // about framework table
        List<String> frameworkNameList = frameworkService.getFrameworkNameList();

        map.put("project_id", randomId); // index(project_id)
        map.put("frameworkList", frameworkNameList); // templateList(frameworkNameList)
        map.put("readmeName", "Readme.md"); // Readme.md
        map.put("springBootVersion", springBootVersion); // springboot 버전
        map.put("groupId", groupId); // groupId
        map.put("artifactId", artifactId); // artifactId
        map.put("javaVersion", javaVersion); // javaVersion
        map.put("databaseName", databaseName); // db명

        return map;
    }

    @PostMapping(value = "/register2")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String repoLink)
        throws IOException, InterruptedException {
        HashMap<String,Object> map = new HashMap<String,Object>();

        System.out.println("repoLink : " + repoLink);
        /* 예외처리
            링크 포맷 : https://github.com/로 시작
            (1) 링크 포맷이 맞지 않는 경우 => LinkFormatError 리턴
            (2) .git이 붙어 있지 않는 링크인 경우 => .git 붙여주기
            (3) 없는 레포지토리 링크(clone이 안되는 경우)일 경우 => LinkFormatError 리턴
        */

        if(!repoLink.contains("https://github.com/")){ // (1) 예외처리
            map.put("error", "LinkFormatError");
            return map;
        }
        if(!repoLink.contains(".git")){ // (2) 예외처리
            repoLink += ".git";
        }

        String repoLinkInfo = repoLink.substring(19);
        System.out.println("repoLinkInfo : " + repoLinkInfo);
        String userName = repoLinkInfo.split("/")[0];

        String repositoryName = repoLinkInfo.split("/")[1].substring(0, repoLinkInfo.split("/")[1].indexOf(".git"));
        System.out.println("userName : " + userName + ", repositoryName : " + repositoryName);

        ProcessBuilder builder = new ProcessBuilder();

        // project table에서 id 가져오기
        randomIdList = projectService.getIdAll();
        String randomId = projectIdGenerate();
        String unzipFilesName = "unzipFiles_" + randomId;
        System.out.println("unzipFilesName : " + unzipFilesName);

        // clone(file name : unzipFiles)
        builder.command("git", "clone", repoLink, unzipFilesName); // mac
        // builder.command("cmd.exe","/c","git", "clone", repoLink, unzipFilesName); // window

        try{
            var clone_process = builder.start();

            try (var reader = new BufferedReader( // clone 완료 후 아래 코드 실행
                new InputStreamReader(clone_process.getInputStream()))) {
                String commandResult;
                while ((commandResult = reader.readLine()) != null) {
                    System.out.println(commandResult);
                }
            }
            builder.directory(new File(unzipFilesName)); // 현재 위치 이동
            builder.start();
        } catch(IOException e){ // (3) 예외처리
            map.put("error", "cloneError");
            System.out.println("map : " + map);
            return map;
        }

        // project architecture
        builder.command("tree"); // mac
        // builder.command("cmd.exe","/c","tree"); // window
        var process = builder.start();

        String architecture = "\n<!-- Project Architecture -->\n";
        architecture += "```bash\n";

        // tree 명령어 실행 후, 콘솔에 출력해주기
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                architecture += commandResult + "   \n";
            }
        }
        architecture += "```\n";

        System.out.println("architecture : " + architecture);

        builder.directory(new File("../backend")); // 원래 위치로 이동
        builder.start();

        // 압축 푼 파일들 중에서 원하는 정보 찾기(ex. url 찾기)
        String searchDirPath = unzipFilesName;
        System.out.println("\n[파일 리스트]");
        int retSearchFiles = 0; // 파일 리스트 다 뽑아냈는지 확인할 수 있는 리턴값
        retSearchFiles = searchFiles(searchDirPath);
        System.out.println("retSearchFiles : " + retSearchFiles);

        //------------- db insert 관련 -------------//
        List<String> javaFileName = new ArrayList<>();
        List<String> javaFilePath = new ArrayList<>();
        List<String> javaFileContent = new ArrayList<>();
        List<String> javaFileDetail = new ArrayList<>();

        for(int i = 0 ; i < file_nameList.size() ; i++){
            if((file_nameList.get(i).contains("pom.xml")) ||
                (file_nameList.get(i).contains(".java") && file_pathList.get(i).contains("src/main/java/")) ||
                (file_pathList.get(i).contains("src/main/resources/application.properties"))){

                javaFileName.add(file_nameList.get(i));
                javaFilePath.add(file_pathList.get(i));
                javaFileContent.add(file_contentList.get(i));

                if((file_nameList.get(i).contains("pom.xml")) ||
                    (file_pathList.get(i).contains("src/main/resources/application.properties"))){
                    javaFileDetail.add("etc"); // 기타
                } else{ // java 파일
                    if(file_contentList.get(i).contains("@RestController")){
                        javaFileDetail.add("controller");
                    } else if(file_contentList.get(i).contains("implements")) {
                        javaFileDetail.add("Impl");
                    } else{ // class
                        javaFileDetail.add("noImpl");
                    }
                }
            }
        }
        if(retSearchFiles == 1){ // 파일 리스트 다 뽑아냈으면 전역변수 초기화
            file_nameList.clear();
            file_pathList.clear();
            file_contentList.clear();
        }

        // project architecture project table에 insert
        projectService.saveProject(randomId, "Project Architecture", "", architecture, "tree");

        // project table에 .java 파일만 insert
        for(int i = 0 ; i < javaFileName.size() ; i++){
            try{
                projectService.saveProject(randomId, javaFileName.get(i), javaFilePath.get(i), javaFileContent.get(i), javaFileDetail.get(i));
            } catch (Exception e){
                System.out.print(javaFileName.get(i)); // 어느 파일이 길이가 긴지 확인
            }
        }

        // user table에 insert
        userService.saveUser(randomId, userName, repositoryName);

        // content data 보냈으므로, 압축풀기한 파일들, 업로드된 zip 파일 모두 삭제
        deleteCloneFiles(builder, unzipFilesName);

        // =============== pom.xml에서 필요한 데이터 파싱 =============== //
        String xmlPath = ""; // test
        String xmlContent = "";
        // =============== application.properties에서 필요한 데이터 파싱 =============== //
        String propertiesPath = ""; // test
        String propertiesContent = "";
        // =============== 디렉토리별 파일 구분 =============== //
        List<String> controllerDir = new ArrayList<>();
        List<String> dtoDir = new ArrayList<>();
        List<String> repositoryDir = new ArrayList<>();
        List<String> daoDir = new ArrayList<>();
        List<String> daoImplDir = new ArrayList<>();
        List<String> entityDir = new ArrayList<>();
        List<String> entityImplDir = new ArrayList<>();
        List<String> handlerDir = new ArrayList<>();
        List<String> handlerImplDir = new ArrayList<>();
        List<String> serviceDir = new ArrayList<>();
        List<String> serviceImplDir = new ArrayList<>();
        List<String> etcDir = new ArrayList<>();

        List<ProjectEntity> getProjectTableRow = projectService.getFileContent(randomId);
        for(int i = 0 ; i < getProjectTableRow.size() ; i++){
            if(getProjectTableRow.get(i).getFile_path().contains("pom.xml")){
                xmlPath = getProjectTableRow.get(i).getFile_path();
                xmlContent = getProjectTableRow.get(i).getFile_content();
            } else if(getProjectTableRow.get(i).getFile_path().contains("application.properties")){
                propertiesPath = getProjectTableRow.get(i).getFile_path(); // test
                propertiesContent = getProjectTableRow.get(i).getFile_content();
            }

            // 주요 자바 파일들 디렉토리별로 구분하기
            if(getProjectTableRow.get(i).getFile_path().contains("CONTROLLER".toLowerCase())){
                controllerDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("DTO".toLowerCase())){
                dtoDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("REPOSITORY".toLowerCase())){
                repositoryDir.add(getProjectTableRow.get(i).getFile_name());
            } else if(getProjectTableRow.get(i).getFile_path().contains("DAO".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    daoImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    daoDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("ENTITY".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    entityImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    entityDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("HANDLER".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    handlerImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    handlerDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else if(getProjectTableRow.get(i).getFile_path().contains("SERVICE".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("Impl")){
                    serviceImplDir.add(getProjectTableRow.get(i).getFile_name());
                } else if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    serviceDir.add(getProjectTableRow.get(i).getFile_name());
                }
            } else{
                etcDir.add(getProjectTableRow.get(i).getFile_name());
            }
        }

        /* test
        System.out.println("controllerDir : " + controllerDir);
        System.out.println("dtoDir : " + dtoDir);
        System.out.println("repositoryDir : " + repositoryDir);
        System.out.println("daoDir : " + daoDir);
        System.out.println("daoImplDir : " + daoImplDir);
        System.out.println("entityDir : " + entityDir);
        System.out.println("entityImplDir : " + entityImplDir);
        System.out.println("handlerDir : " + handlerDir);
        System.out.println("handlerImplDir : " + handlerImplDir);
        System.out.println("serviceDir : " + serviceDir);
        System.out.println("serviceImplDir : " + serviceImplDir);
        System.out.println("etcDir : " + etcDir);
         */

        // 공백 제거한 xmlContent - 정규식을 쓰기 위해 줄바꿈 제거
        String noWhiteSpaceXml = xmlContent.replaceAll("\n", "");

        // 필요한 데이터 : 스프링부트 버전, 패키지명, 자바 jdk 버전, (+ dependency 종류)
        String springBootVersion = findSpringBootVersion(noWhiteSpaceXml);
        List<String> packageName = findPackageName(noWhiteSpaceXml);
        String groupId = packageName.get(0);
        String artifactId = packageName.get(1);
        String javaVersion = findJavaVersion(noWhiteSpaceXml);
        // String dependencies = findDependencies(noWhiteSpaceXml); // 미완

        // 공백 제거한 propertiesContent - 정규식을 쓰기 위해 줄바꿈 제거
        String noWhiteSpaceProperties = propertiesContent.replaceAll("\n", "");

        // 필요한 데이터 : 사용하는 database명
        String databaseName = findDatabaseName(noWhiteSpaceProperties);

        //----------- db select in framework table -----------//
        // about framework table
        List<String> frameworkNameList = frameworkService.getFrameworkNameList();

        map.put("project_id", randomId); // index(project_id)
        map.put("frameworkList", frameworkNameList); // templateList(frameworkNameList)
        map.put("readmeName", "Readme.md"); // Readme.md
        map.put("springBootVersion", springBootVersion); // springboot 버전
        map.put("groupId", groupId); // groupId
        map.put("artifactId", artifactId); // artifactId
        map.put("javaVersion", javaVersion); // javaVersion
        map.put("databaseName", databaseName); // db명

        return map;
    }

    public static int searchFiles(String searchDirPath) throws IOException {
        File dirFile = new File(searchDirPath);
        File[] fileList = dirFile.listFiles();

        if(dirFile.exists()){
            for(int i = 0 ; i < fileList.length; i++) {
                if(fileList[i].isFile()) {
                    file_pathList.add(fileList[i].getPath());
                    file_nameList.add(fileList[i].getName());
                    System.out.println("\n" + fileList[i].getPath()); // Full Path

                    Scanner reader2 = new Scanner(new File(fileList[i].getPath()));
                    // file_content 찾기
                    String tempStr = "";
                    while (reader2.hasNextLine()) { // find file_content
                        String str = reader2.nextLine();
                        tempStr = tempStr + str + "\n";
                    }
                    file_contentList.add(tempStr);
                } else if(fileList[i].isDirectory()) {
                    searchFiles(fileList[i].getPath());  // 재귀함수 호출
                }
            }
        } else{
            System.out.println("!!! 탐색할 파일이 존재하지 않습니다 !!!");
        }

        return 1; // 전역변수 초기화하기 위한 리턴값 반환
    }

    public static void deleteCloneFiles(ProcessBuilder builder, String unzipFilesName) throws IOException { // register2
        /* mac */
        builder.command("rm", "-rf", unzipFilesName);
        builder.start();

        /* window
        builder.command("cmd.exe","/c","rmdir", unzipFilesName);
        builder.start();
        */

        System.out.println("clone한 파일들 삭제 완료!!");
    }

    public static void deleteUnzipFiles(ProcessBuilder builder) throws IOException { // register1
        // upzip한 파일들, zip파일 모두 삭제
        /* mac */
        builder.command("rm", "-rf", "./unzipFiles/");
        builder.start();
        builder.command("rm", "-rf", "./unzipTest.zip");
        builder.start();

        /* window
        builder.command("cmd.exe","/c","rmdir", "unzipFiles");
        builder.start();
        builder.command("cmd.exe","/c","del", "unzipTest.zip");
        builder.start();
        */

        System.out.println("업로드된 zip파일, 압축풀기한 파일들 모두 삭제 완료!!");
    }

    @PostMapping("/framework")
    public String saveData(@RequestParam("project_id") String project_id,
        @RequestParam("framework_name") String framework_name) throws IOException {
        String frame_content = "";
        UserDTO userDTO = userService.getUser(project_id);
        String user_name = userDTO.getUser_name();
        String repo_name = userDTO.getRepository_name();

        // framework_id에 따른 content제공
        if(framework_name.equals("Contributor")){
            frame_content = frameworkService.findContent(framework_name);
            frame_content = frame_content.replace("repositoryName", repo_name);
            frame_content = frame_content.replace("userName", user_name);
        } else if (framework_name.equals("Header")) { /* header 값에 대한 framework*/
            String Header = "header";
            frame_content = frameworkService.findContent(Header);
            frame_content=frame_content.replace("repoName",repo_name);
        } else if (framework_name.equals("Period")) {
            String Period = "Period";
            frame_content = frameworkService.findContent(Period);
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png");
            frame_content=frame_content.replace("startDate", "Start Date");
            frame_content=frame_content.replace("endDate", "End Date");
        } else if(framework_name.equals("WebAPI")) {
            frame_content = frameworkService.findContent("WebAPI");
            frame_content += webAPI(project_id);
        } else if (framework_name.equals("Social")){
            frame_content = "### Social<br>";
            String url = "https://github.com/";
            url = url + user_name;
            String[] social_link = {"instagram", "facebook", "linkedin", "notion", "twitter", "github", "gmail"};
            String[] logo_color = {"E4405F","1877F2","0A66C2","000000","1DA1F2","181717","F06B66" };
            String social_temp =" ";
            social_temp = frameworkService.findContent("Social");

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("vcard-details");
            for (Element headline : elements) {
                String[] urlparsing = headline.text().split(" ");
                for (int i = 0; i < urlparsing.length; i++) {
                    System.out.println(urlparsing[i]);
                    for( int j = 0; j< social_link.length; j++){
                        if(urlparsing[i].contains(social_link[j])){
                            String temp= social_link[j]+"_Link";
                            String temp_data=" ";
                            temp_data=social_temp.replace("logo_color",logo_color[j]);
                            temp_data=temp_data.replace("social",social_link[j]);
                            temp_data=temp_data.replace(temp, urlparsing[i]);
                            frame_content +=temp_data;
                            System.out.println(frame_content);
                        }
                    }
                }
            }
        } else if (framework_name.equals("Dependency")) {
            String Dependency = "Dependency";
            String xmlContent = "";
            List<ProjectEntity> getProjectTableRow = projectService.getFileContent(project_id);
            for(int i = 0 ; i < getProjectTableRow.size() ; i++) {
                if (getProjectTableRow.get(i).getFile_path().contains("pom.xml")) {
                    xmlContent = getProjectTableRow.get(i).getFile_content();
                }
            }

            String noWhiteSpaceXml = xmlContent.replaceAll("\n", "");
            String dependencyTags = "\n" + findDependencies(noWhiteSpaceXml).get(1).toString();
            List<String> dependencyNameList = (List<String>) findDependencies(noWhiteSpaceXml).get(0);
            String dependencyName = "\n";
            String dependencyBtn = "<a href=\"https://mvnrepository.com/\"><img src=\"https://img.shields.io/badge/NUM-DEPENDENCYNAME-9cf\"></a>";

            for(int i = 0 ; i < dependencyNameList.size() ; i++){
                String tempBtn = dependencyBtn;
                String dependencyFormat = dependencyNameList.get(i);
                dependencyFormat = dependencyFormat.replace("-", "--");
                tempBtn = tempBtn.replace("NUM", Integer.toString(i+1));
                tempBtn = tempBtn.replace("DEPENDENCYNAME",  dependencyFormat);

                dependencyName = dependencyName + tempBtn + "   ";
            }
            frame_content = frameworkService.findContent(Dependency);
            frame_content=frame_content.replace("DependencyNames", dependencyName);
            frame_content=frame_content.replace("DependencyContents", dependencyTags);
        } else if (framework_name.equals("DB Table")) {
            frame_content = frameworkService.findContent("DB Table");
            frame_content += dbTable(project_id);
        } else if (framework_name.equals("License")) {
            String License_file = "default";
            System.out.println("Project id : " + project_id);
            List<ProjectEntity> getProjectTableRow = projectService.getFileContent(project_id);
            for (int i = 0; i < getProjectTableRow.size(); i++) {
                if (getProjectTableRow.get(i).getFile_path().contains("LICENSE")) {
                    String str = getProjectTableRow.get(i).getFile_content();
                    String firstLine = str.substring(0, str.indexOf("\n"));
                    firstLine = firstLine.replace("License", "");
                    firstLine = firstLine.trim();
                    frame_content = "## License\n" +
                        "![License: MPL 2.0](https://img.shields.io/badge/License_name-brightgreen.svg)";
                    frame_content = frame_content.replace("License_name", firstLine);
                    System.out.println(firstLine);
                    License_file = "exist";
                }
            }
            if (License_file.equals("default")) {
                frame_content = "## License\n" +
                    "The MIT License (MIT)\n" +
                    "\n" +
                    "Copyright (c) 2023 UserName\n" +
                    "\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n"
                    +
                    "\n" +
                    "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n"
                    +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.";
                frame_content = frame_content.replace("UserName", user_name);
            }
        } else if (framework_name.equals("Architecture")) {
            frame_content = frameworkService.findContent("Architecture");
            String architecture = projectService.getFileContentByFileName(project_id, "Project Architecture");
            frame_content += architecture;
        }

        return frame_content;
    }

    public String dbTable(String project_id){
        String dbTable = "\n<!-- DB Table -->\n";

        // entity parsing 하기 위해 entity 파일 찾기
        List<String> entityDir = new ArrayList<>();
        List<String> entityDirContent = new ArrayList<>();
        List<ProjectEntity> getProjectTableRow = projectService.getFileContent(project_id);

        for(int i = 0 ; i < getProjectTableRow.size() ; i++){
            if(getProjectTableRow.get(i).getFile_path().contains("ENTITY".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    entityDir.add(getProjectTableRow.get(i).getFile_name());
                    entityDirContent.add(getProjectTableRow.get(i).getFile_content());
                }
            }
        }
        int tableLen = entityDir.size();
        System.out.println("Table 개수 : " + tableLen);

        for(int i = 0 ; i < tableLen ; i++) {
            String frameworkContent = entityDirContent.get(i);
            System.out.println("table content : " + frameworkContent);
            // @Table이 없어서 에러 뜨는 경우 - BaseEntity.java의 경우
            // 에러뜨는 경우 pass하도록 예외 처리
            int tableIdx = frameworkContent.indexOf("@Table(");
            System.out.println("tableIdx : " + tableIdx);
            if(tableIdx == -1){
                continue;
            }
            String tableNameLine = frameworkContent.substring(frameworkContent.indexOf("@Table("),
                frameworkContent.indexOf(")") + 1);
            String tableName = tableNameLine.split("\"")[1];
            System.out.println("table name : " + tableName);

            dbTable += "#### 🌱 " + tableName + " Table\n"
                + "|*Column Name*|\n"
                + "|----|\n";

            // 주석처리 라인 지우기
            int startIdx = 0, endIdx = 0;
            List<String> commentLineList = new ArrayList<>();
            String commentLine = "";
            while(true) {
                // indexOf(String str, int fromIndex)
                startIdx = frameworkContent.indexOf("//", endIdx);
                endIdx = frameworkContent.indexOf("\n", startIdx);

                if (startIdx < 0) { // 주석처리 없는 경우 스킵
                    break;
                } else { // 주석처리 있는 경우 그 라인 리스트에 담기
                    commentLine = frameworkContent.substring(startIdx, endIdx);
                    commentLineList.add(commentLine);
                }
            }

            for(int k = 0 ; k < commentLineList.size() ; k++){ // 주석 라인들 다 지우기
                frameworkContent = frameworkContent.replace(commentLineList.get(k), "");
            }

            // 공백 제거한 xmlContent - 정규식을 쓰기 위해 줄바꿈 제거
            String noWhiteSpaceContent = frameworkContent.replaceAll("\n", " ");

            // class { 이후 내용만 get
            Pattern pattern4 = Pattern.compile("(class )(.*?)(\\{)");
            Matcher matcher4 = pattern4.matcher(noWhiteSpaceContent);
            while (matcher4.find()) {
                int afterBraceIdx = noWhiteSpaceContent.indexOf(matcher4.group(3).trim());
                noWhiteSpaceContent = noWhiteSpaceContent.substring(afterBraceIdx); // afterBrace
            }

            // column name parsing
            String[] dataType = {"String", "int", "long", "boolean", "char", "byte", "short", "float", "double"};

            for(int j = 0 ; j < dataType.length ; j++){
                String type = dataType[j];
                String pkColumn = "";

                if (noWhiteSpaceContent.contains(type)) {
                    Pattern pattern = Pattern.compile("(@Id )(.*?)(;)"); // find PK
                    Matcher matcher = pattern.matcher(noWhiteSpaceContent);

                    while (matcher.find()) {
                        pkColumn = matcher.group(2).trim() + matcher.group(3).trim();

                        // pk인 컬럼 추가
                        Pattern pattern2 = Pattern.compile("(" + type + " )(.*?)(;)");
                        Matcher matcher2 = pattern2.matcher(pkColumn);
                        while (matcher2.find()) {
                            String columnName = matcher2.group(2).trim() + " **(PK)**";
                            dbTable += "|" + columnName + "|\n";

                            // pkColumn 제거
                            noWhiteSpaceContent = noWhiteSpaceContent.replaceAll("@Id", "");
                            noWhiteSpaceContent = noWhiteSpaceContent.replaceAll(matcher2.group(), "");
                        }
                    }

                    // pk 아닌 컬럼 테이블에 추가
                    Pattern pattern3 = Pattern.compile("(" + type + " )(.*?)(;)");
                    Matcher matcher3 = pattern3.matcher(noWhiteSpaceContent);
                    while (matcher3.find()) {
                        String columnName = matcher3.group(2).trim();
                        dbTable += "|" + columnName + "|\n";
                    }
                }
            }
        }

        return dbTable;
    }

    public String webAPI(String projectId){
        List<ProjectEntity> result = projectService.getController(projectId);
        String mdResult = "<!-- Web API -->\n"
            + "|HTTP|API|URL|Return Type|Parameters|\n"
            + "|----|----|---|---|---|\n";

        System.out.println(result);
        int start_index = 0, end_index = 0;
        String urlTemp, returnType, parameters;
        String[] apiTemp;
        String current_content;

        for(int i = 0; i < result.size(); i++){
            current_content = result.get(i).getFile_content();
            mdResult += "|**"+  result.get(i).getFile_name()+"**|\n";

            // find post mapping
            while(true){
                // indexOf(String str, int fromIndex)
                start_index = current_content.indexOf("@PostMapping(", end_index);
                end_index = current_content.indexOf(")", start_index);

                if(start_index < 0){
                    break;
                } else{
                    urlTemp = current_content.substring(start_index,end_index);
                    urlTemp = urlTemp.split("\"")[1];

                    start_index = current_content.indexOf("public", end_index);
                    end_index = current_content.indexOf("(", start_index);
                    apiTemp = current_content.substring(start_index,end_index).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    start_index = current_content.indexOf("(", end_index);
                    end_index = current_content.indexOf("{", start_index);
                    parameters = current_content.substring(start_index+1,end_index);
                    parameters = parameters.substring(0,parameters.lastIndexOf(")"));
                    parameters= parameters.replace("," ,"<br>");
                    parameters= parameters.replace("\n" ," ");

                    mdResult += "| Post |" +
                        apiTemp[apiTemp.length-1]+"()" + "|" +
                        urlTemp + "|"+
                        returnType +"|"+
                        parameters +"|\n";
                }
            }
            // find get mapping
            while(true){
                start_index = current_content.indexOf("@GetMapping(", end_index);
                end_index = current_content.indexOf(")", start_index);

                if(start_index < 0){
                    break;
                } else{
                    urlTemp = current_content.substring(start_index,end_index);
                    urlTemp = urlTemp.split("\"")[1];

                    start_index = current_content.indexOf("public", end_index);
                    end_index = current_content.indexOf("(", start_index);
                    apiTemp = current_content.substring(start_index,end_index).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    start_index = current_content.indexOf("(", end_index);
                    end_index = current_content.indexOf("{", start_index);
                    parameters = current_content.substring(start_index+1,end_index);
                    parameters = parameters.substring(0,parameters.lastIndexOf(")"));
                    parameters= parameters.replace("," ,"<br>");
                    parameters= parameters.replace("\n" ," ");

                    mdResult += "| Get |" +
                        apiTemp[apiTemp.length-1]+"()" + "|" +
                        urlTemp + "|"+
                        returnType +"|"+
                        parameters +"|\n";
                }
            }
        }
        return mdResult;
    }

    @PostMapping("/editPeriod")
    public String editPeriodImage(
        @RequestParam("start_date") String start_date,
        @RequestParam("end_date") String end_date) {
        String frame_content = frameworkService.findContent("Period");

        if(start_date.equals("no")){
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png"); // ing
            frame_content=frame_content.replace("startDate", "Start Date");
            frame_content=frame_content.replace("endDate", "End Date");
        }
        else if(end_date.equals("no")) { // end 입력이 안되면
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png"); // ing
            frame_content=frame_content.replace("startDate", start_date);
            frame_content=frame_content.replace("endDate", "End Date");
        } else{ // start date와 end date 모두 입력되었을 때
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/LGBnpy.png"); // finished
            frame_content=frame_content.replace("startDate", start_date);
            frame_content=frame_content.replace("endDate", end_date);
        }

        return frame_content;
    }

    @PostMapping("/alldata")
    public String alldata(@RequestParam("project_id") String project_id) {
        String frame_content = "";
        UserDTO userDTO = userService.getUser(project_id);
        String user_name = userDTO.getUser_name();
        String repo_name = userDTO.getRepository_name();

        frame_content = "Header_check\n" +
            "\n" +
            "\n" +
            "Contributor_check\n" +
            "<div style=\"font-weight:bold; font-size: 21px;\">Project Period</div>" +
            "<div><img src='https://ifh.cc/g/LGBnpy.png' width=100%></div>" +
            "<span style=\"width:20%\"><span/>" +
            "<span style=\"margin-right: 55%; margin-left: 5%;\">Start Date</span>" +
            "<span width=20%>End Date</span></br>   " +
            "\n" +
            "\n"+
            "## Table of contents[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#table-of-contents)   </br>\n" +
            "- [Install](#install)\n" +
            "- [DB](#db)\n" +
            "- [queryMethod](#querymethod)\n" +
            "---\n" +
            "\n" +
            "## Install[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#install)\n" +
            "<div align=\"right\">[ <a href=\"#table-of-contents\">↑ to top ↑</a> ]</div>\n" +
            "1. Java 설치\n" +
            "   - Spring Boot를 사용하려면 Java 8 버전 이상이 필요합니다.<br><br />\n" +
            "   - [Oracle Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) .<br><br />\n" +
            "\n" +
            "\n" +
            "```\n" +
            "sdk install spring boot\n" +
            "```\n" +
            "2. Spring Boot CLI 설치\n" +
            "\n" +
            "   Spring Boot CLI는 Spring Boot 애플리케이션을 빠르게 만들 수 있는 명령줄 도구입니다.\n" +
            "```\n" +
            " spring init --dependencies=web myproject\n" +
            "```\n" +
            "## DB[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#db)\n" +
            "<div align=\"right\">[ <a href=\"#table-of-contents\">↑ to top ↑</a> ]</div>\n" +
            "\n" +
            "\n" +
            "## QueryMethod[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#querymethod)\n" +
            "<div align=\"right\">[ <a href=\"#table-of-contents\">↑ to top ↑</a> ]</div>\n" +
            "\n" +
            "- 데이터베이스에서 name이 \"John Doe\"이거나 age가 18 이상인 Person 엔티티를 조회하는 쿼리 메소드\n" +
            "```\n" +
            " public interface PersonRepository extends JpaRepository<Person, Long> {\n" +
            "    List<Person> findByNameOrAgeGreaterThanEqual(String name, int age);\n" +
            "}\n" +
            "```\n" +
            "- 데이터베이스에서 age가 18 이상인 Person 엔티티를 age를 기준으로 오름차순으로 조회하는 쿼리 메소드\n" +
            "```\n" +
            " public interface PersonRepository extends JpaRepository<Person, Long> {\n" +
            "    List<Person> findByAgeGreaterThanEqualOrderByAgeAsc(int age);\n" +
            "}\n" +
            "```\n";

        List<String> frameworkNameList = frameworkService.getFrameworkNameList();
        for(int i = 0 ; i < frameworkNameList.size() ; i++){
            String temp=frameworkNameList.get(i) ;
            frame_content=frame_content.replace(temp+"_check",frameworkService.findContent(temp));
        }
        frame_content= frame_content.replace("userName",user_name);
        frame_content= frame_content.replace("repositoryName",repo_name);

        return frame_content;
    }
}