package com.readme.rss.data.service.Impl;

import static java.lang.Thread.sleep;
import com.readme.rss.data.service.RegisterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class RegisterServiceImpl implements RegisterService {

  private List<String> file_pathList = new ArrayList<>();
  private List<String> file_nameList = new ArrayList<>();
  private List<String> file_contentList = new ArrayList<>();


  public String findSpringBootVersion(String xmlContent) {
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


  public int searchFiles(String searchDirPath) throws IOException {
    File dirFile = new File(searchDirPath);
    File[] fileList = dirFile.listFiles();

    if(dirFile.exists()){
      for(int i = 0 ; i < fileList.length; i++) {
        if(fileList[i].isFile()) {
          file_pathList.add(fileList[i].getPath());
          file_nameList.add(fileList[i].getName());

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

  public void deleteCloneFiles(ProcessBuilder builder, String unzipFilesName) throws IOException { // register2
    try{
      /* mac */
      builder.command("rm", "-rf", unzipFilesName);
      builder.start();

      /* window
      builder.command("cmd.exe","/c","rmdir", unzipFilesName);
      builder.start();*/


      System.out.println("clone한 파일들 삭제 완료!!");
    } catch(IOException e){
      System.out.println("clone한 파일들 삭제 실패");
    }

  }

  public List<String> findPackageName(String xmlContent) {
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

  public String findJavaVersion(String xmlContent) {
    String javaVersion = "";
    Pattern pattern = Pattern.compile("(?<=\\<java.version>)(.*?)(?=<\\/java.version>)");
    Matcher matcher = pattern.matcher(xmlContent);
    if (matcher.find()) {
      javaVersion = matcher.group();
    }

    return javaVersion;
  }

  public String findDatabaseName(String propertiesContent) {
    String databaseName = "";

    // Pattern pattern = Pattern.compile("(jdbc:)(.*?)(://)"); // 전후 문자열 포함한 문자열 추출
    Pattern pattern = Pattern.compile("(\\bjdbc:\\b)(.*?)(\\b://\\b)");
    Matcher matcher = pattern.matcher(propertiesContent);
    if (matcher.find()) {
      databaseName = matcher.group(2).trim();
    }

    return databaseName;
  }

  public HashMap<String, Object> registerLink(String repoLink, String projectId) throws IOException {
    HashMap<String,Object> map = new HashMap<>();
        /* 예외처리
            링크 포맷 : https://github.com/로 시작
            (1) 링크 포맷이 맞지 않는 경우 => LinkFormatError 리턴
            (2) .git이 붙어 있지 않는 링크인 경우 => .git 붙여주기
            (3) 없는 레포지토리 링크(clone이 안되는 경우)일 경우 => cloneError 리턴
        */

    ProcessBuilder builder = new ProcessBuilder();

    String unzipFilesName = "unzipFiles_" + projectId;

    //clone(file name : unzipFiles_projectId)
    builder.command("sudo", "git", "clone", repoLink, unzipFilesName); // mac
    // builder.command("cmd.exe","/c","git", "clone", repoLink, unzipFilesName); // window

    try {
      Process cloneProcess = builder.start();
      int exitCode = cloneProcess.waitFor();
      if (exitCode == 0) {
        System.out.println("Git clone command executed successfully.");
      } else {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(cloneProcess.getErrorStream()))) {
          String errorMessage;
          while ((errorMessage = reader.readLine()) != null) {
            System.out.println(errorMessage);
          }
        }
        System.out.println("Git clone command execution failed with exit code: " + exitCode);
        builder.directory(new File(unzipFilesName)); // 현재 위치 이동
        builder.start();
      }
    } catch (IOException e) {
      System.out.println("IOException occurred: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Thread interrupted: " + e.getMessage());
    }

    // project architecture
    builder.command("tree"); // mac
    // builder.command("cmd.exe","/c","tree"); // window
    var process = builder.start();

    String architecture = "\n<!-- Project Architecture -->\n";
    architecture += "```bash\n";
    try (var reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      String commandResult;
      while ((commandResult = reader.readLine()) != null) {
        architecture += commandResult + "   \n";
      }
    }
    architecture += "```\n";
    builder.directory(new File("../")); // 원래 위치로 이동
    builder.start();

    String searchDirPath = unzipFilesName;
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
          (file_pathList.get(i).contains("src/main/resources/application.properties"))||
          (file_nameList.get(i).contains("License"))
      ){

        javaFileName.add(file_nameList.get(i));
        javaFilePath.add(file_pathList.get(i));
        javaFileContent.add(file_contentList.get(i));

        if((file_nameList.get(i).contains("pom.xml")) ||
            (file_pathList.get(i).contains("src/main/resources/application.properties"))){
          javaFileDetail.add("etc"); // 기타
        }else if(file_nameList.get(i).contains("LICENSE")){
          javaFileDetail.add("license"); // 기타
        }else{ // java 파일
          if(file_contentList.get(i).contains("@RestController")
              || file_contentList.get(i).contains("@Controller")
          ){
            javaFileDetail.add("controller");
          }else if(file_contentList.get(i).contains("@Service")
          ) {
            javaFileDetail.add("service");
          }else if(file_contentList.get(i).contains("@Repository")
              ||file_contentList.get(i).contains("extends JpaRepository")
              ||file_nameList.get(i).toLowerCase().contains("repository")
          ){
            javaFileDetail.add("repository");
          }else if(file_contentList.get(i).contains("@Entity")) {
            javaFileDetail.add("entity");
          }else if(file_contentList.get(i).contains("@Data")
              ||file_nameList.get(i).toLowerCase().contains("dto")
          ){
            javaFileDetail.add("dto");
          }else if(file_contentList.get(i).contains("implements")) {
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

    map.put("Architecture", architecture);
    map.put("javaFileName", javaFileName);
    map.put("javaFilePath", javaFilePath);
    map.put("javaFileContent", javaFileContent);
    map.put("javaFileDetail", javaFileDetail);

    // content data 보냈으므로, 압축풀기한 파일들, 업로드된 zip 파일 모두 삭제
    deleteCloneFiles(builder, unzipFilesName);
    return map;
  }


  @Override
  public HashMap<String, Object> parseData(String noWhiteSpaceXml, String propertiesContent){
    HashMap<String, Object> map = new HashMap<String, Object>();

    // 필요한 데이터 : 스프링부트 버전, 패키지명, 자바 jdk 버전, (+ dependency 종류)
    String springBootVersion = findSpringBootVersion(noWhiteSpaceXml);
    List<String> packageName = findPackageName(noWhiteSpaceXml);
    String groupId = packageName.get(0);
    String artifactId = packageName.get(1);
    String javaVersion = findJavaVersion(noWhiteSpaceXml);

    // 공백 제거한 propertiesContent - 정규식을 쓰기 위해 줄바꿈 제거
    String noWhiteSpaceProperties = propertiesContent.replaceAll("\n", "");

    // 필요한 데이터 : 사용하는 database명
    String databaseName = findDatabaseName(noWhiteSpaceProperties);

    map.put("springBootVersion", springBootVersion);
    map.put("packageName", packageName);
    map.put("groupId", groupId);
    map.put("artifactId", artifactId);
    map.put("javaVersion", javaVersion);
    map.put("databaseName", databaseName);

    return map;
  }
}
