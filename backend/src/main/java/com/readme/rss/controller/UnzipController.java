package com.readme.rss.controller;

import static java.lang.Thread.sleep;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.repository.FrameworkRepository;
import com.readme.rss.data.repository.ProjectRepository;
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
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UnzipController {
    private ProjectService projectService;
    private UserService userService;
    private ProjectRepository projectRepository;
    private FrameworkRepository frameworkRepository;

    @Autowired
    public UnzipController(ProjectService projectService, UserService userService, ProjectRepository projectRepository, FrameworkRepository frameworkRepository) {
        this.projectService = projectService;
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.frameworkRepository = frameworkRepository;
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

    @PostMapping(value = "/register")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String jsonParam1,
        @RequestParam("jsonParam2") String jsonParam2, @RequestParam("file") MultipartFile file)
        throws IOException, InterruptedException {
        HashMap<String,Object> map = new HashMap<String,Object>();

        String fileName = file.getOriginalFilename();
        String userName = jsonParam1;
        String repositoryName = jsonParam2;

        System.out.println("userName : " + userName);
        System.out.println("repositoryName : " + repositoryName);

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
        Path savePath = Paths.get("./unzipTest.zip"); // unzipTest.zip이름으로 저장
        file.transferTo(savePath); // 파일 다운로드

        ProcessBuilder builder = new ProcessBuilder();

        deleteUnzipFiles(builder);

        // unzipFiles 폴더 생성 - 압축풀기한 파일들을 저장하는 임시 폴더
        //builder.command("mkdir", "unzipFiles"); // mac
        builder.command("cmd.exe","/c","mkdir", "unzipFiles"); // window
        builder.start();

        // 파일 압축 풀기
        // builder.command("unzip", "unzipTest.zip", "-d", "./unzipFiles"); // mac
        builder.command("cmd.exe","/c","unzip", "unzipTest.zip", "-d", "./unzipFiles"); // window
        var process = builder.start(); // upzip 실행

        // unzip 실행 후, 콘솔에 출력해주기
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                System.out.println(commandResult);
            }
        }

        // 압축 푼 파일들 중에서 원하는 정보 찾기(ex. url 찾기)
        String searchDirPath = "./unzipFiles";
        System.out.println("\n[압축 해제한 폴더 속 파일 리스트]");
        String content = searchFiles(searchDirPath); // react response로 보내줄 파일에서 찾은 content

        //------------- db insert 관련 -------------//
        // project table에서 id 가져오기
        randomIdList = projectRepository.findDistinctId();
        System.out.println("\nDistinct Project Id : " + randomIdList);

        String randomId = projectIdGenerate();
        System.out.println("randomId : " + randomId);

        // project table에 insert
        for(int i = 0 ; i < file_nameList.size() ; i++){
            // System.out.println(file_pathList.get(i));
            projectService.saveProject(randomId, file_nameList.get(i), file_pathList.get(i), file_contentList.get(i));
        }

        // user table에 insert
        userService.saveUser(randomId, userName, repositoryName);

        // content data 보냈으므로, 압축풀기한 파일들, 업로드된 zip 파일 모두 삭제
        deleteUnzipFiles(builder);

        //----------- db select in framework table -----------//
        // about framework table
        List<String> frameworkNameList = frameworkRepository.findAllName();

        // file_nameList에 확장자를 md로 바꿔주기
        List<String> filenameMD_list = new ArrayList<>();
        for(int i = 0 ; i < file_nameList.size() ; i++){
            int idx = file_nameList.get(i).indexOf('.');
            String md_filename = file_nameList.get(i).substring(0, idx) + ".md";
            filenameMD_list.add(md_filename);
        }

        // map data : index(project_id), templateList(frameworkNameList), readmeName(fileName)
        map.put("project_id", randomId);
        map.put("templateList", frameworkNameList);
        map.put("readmeName", filenameMD_list);

        System.out.println("map data : " + map);

        return map;
    }

    public static String searchFiles(String searchDirPath) throws IOException {
        String findURL = "";
        String findURL_format = "";

        File dirFile = new File(searchDirPath);
        File[] fileList = dirFile.listFiles();

        // 전역변수 초기화
        file_pathList.clear();
        file_nameList.clear();
        file_contentList.clear();

        if(fileList.length == 0){ // 압축풀기가 되지 않은 상태
            System.out.println("!!! 압축풀기할 파일이 존재하지 않습니다 !!!");
        } else{
            for(int i = 0 ; i < fileList.length; i++) {
                if(fileList[i].isFile()) {
                    file_pathList.add(fileList[i].getPath());
                    file_nameList.add(fileList[i].getName());
                    System.out.println("\n" + fileList[i].getPath()); // Full Path

                    Scanner reader = new Scanner(new File(fileList[i].getPath()));

                    // url 찾아서 파싱
                    String tempStr = "";
                    while (reader.hasNextLine()) {
                        String str = reader.nextLine();

                        // find file_content
                        tempStr = tempStr + str + "\n";

                        if (str.contains("url=")) {
                            System.out.println("\n[찾는 content]");
                            findURL = str.substring(5, str.length() - 1);
                            System.out.println(findURL);

                            // url로 넘겨질 포맷으로 변경(특수문자 '/' : %2F)
                            findURL_format = findURL.replaceAll("/", "%2F");
                            System.out.println("\n[url 특수문자 형식 변환]");
                            System.out.println(findURL_format);
                            System.out.println();
                        }
                    }
                    System.out.println(fileList[i].getName() + " 파일 내용 :\n" + tempStr);
                    file_contentList.add(tempStr);
                } else if(fileList[i].isDirectory()) {
                    searchFiles(fileList[i].getPath());  // 재귀함수 호출
                }
            }
        }

        // return findURL_format; // url에 사용할 수 있는 포맷
        return findURL;
    }

    public static void deleteUnzipFiles(ProcessBuilder builder) throws IOException {
        // upzip한 파일들, zip파일 모두 삭제
        /* mac
        builder.command("rm", "-rf", "./unzipFiles/");
        builder.start();
        builder.command("rm", "-rf", "./unzipTest.zip");
        builder.start();
        */
        /* window*/
        builder.command("cmd.exe","/c","rmdir", "unzipFiles");
        builder.start();
        builder.command("cmd.exe","/c","del", "unzipTest.zip");
        builder.start();


        System.out.println("업로드된 zip파일, 압축풀기한 파일들 모두 삭제 완료!!");
    }

    @PostMapping("/framework")
    public String saveData(@RequestParam("project_id") String project_id,
        @RequestParam("framework_name") String framework_name) {
        // 여기서 사용자가 누구인지 index값으로 알아내기
        String frame_content = "";
        System.out.println(project_id+framework_name+"파라미터 체크");
        UserDTO userDTO = userService.getUser(project_id);
        String user_name = userDTO.getUser_name();
        String repo_name = userDTO.getRepository_name();

        // framework_id에 따른 content제공
        if(framework_name.equals("Contributor")){
            frame_content = frameworkRepository.findcontent(framework_name);
            System.out.println(frame_content + "frame content가 제대로 들어왔는지 확인");
            frame_content = frame_content.replace("repositoryName", repo_name);
            frame_content = frame_content.replace("userName", user_name);

            /* header 값에 대한 framework*/
        } else if (framework_name.equals("Header")) {
            String Header = "Header";
            frame_content = frameworkRepository.findcontent(Header);
            frame_content=frame_content.replace("repoName",repo_name);
        } else if (framework_name.equals("Period")) {
            String Period = "Period";
            frame_content = frameworkRepository.findcontent(Period);
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png");
            frame_content=frame_content.replace("startDate", "Start Date");
            frame_content=frame_content.replace("endDate", "End Date");
            System.out.println(frame_content);
        }
        System.out.println(frame_content);
        return frame_content;
    }

    @PostMapping("/editPeriod")
    public String editPeriodImage(
        @RequestParam("start_date") String start_date,
        @RequestParam("end_date") String end_date) {
        String frame_content = "";
        frame_content = frameworkRepository.findcontent("Period");
        if(start_date.equals("no")){
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png");
            frame_content=frame_content.replace("startDate", "Start Date");
            frame_content=frame_content.replace("endDate", "End Date");
        }
        else if(end_date.equals("no")) { // end 입력이 안되면
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png"); // ing
            frame_content=frame_content.replace("startDate", start_date);
            frame_content=frame_content.replace("endDate", "End Date");
        } else{
            frame_content=frame_content.replace("PeriodImage", "https://ifh.cc/g/LGBnpy.png"); // finished
            frame_content=frame_content.replace("startDate", start_date);
            frame_content=frame_content.replace("endDate", end_date);
        }
        System.out.println(frame_content);

        return frame_content;
    }

    @PostMapping("/alldata")
    public String alldata(@RequestParam("project_id") String project_id,
        @RequestParam("framework_name") String framework_name) {
        // 여기서 사용자가 누구인지 index값으로 알아내기
        String frame_content = "";
        System.out.println(project_id+framework_name+"파라미터 체크");
        UserDTO userDTO = userService.getUser(project_id);
        String user_name = userDTO.getUser_name();
        String repo_name = userDTO.getRepository_name();

        frame_content = "Header_check\n" +
            "\n" +
            "\n" +
            "Contributor_check\n" +
            "<div style=\"font-weight:bold; font-size: 21px;\">Project Period</div>" +
            "<div><img src=\"PeriodImage\" width=100%></div>" +
            "<span style=\"width:20%\"><span/>" +
            "<span style=\"margin-right: 55%; margin-left: 5%;\">startDate</span>" +
            "<span width=20%>endDate</span>" +
            "\n" +
            "Period_check\n" +
            "\n" +
            "\n" +
            "---\n" +
            "## Table of contents[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#table-of-contents)\n" +
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

        List<String> frameworkNameList = frameworkRepository.findAllName();
        for(int i = 0 ; i < frameworkNameList.size() ; i++){
            System.out.println(frameworkNameList.get(i) +" check data");
            String temp=frameworkNameList.get(i) ;
            System.out.println(frameworkRepository.findcontent(temp));
            frame_content=frame_content.replace(temp+"_check",frameworkRepository.findcontent(temp));
        }
        frame_content= frame_content.replace("userName",user_name);
        frame_content= frame_content.replace("repositoryName",repo_name);

        return frame_content;
    }
}
