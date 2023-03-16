package com.readme.rss.controller;

import static java.lang.Thread.sleep;

import com.readme.rss.data.dto.TemplateDTO;
import com.readme.rss.data.service.TemplateService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3005")
@RestController
// @RequestMapping("/readme")
public class UnzipController {
    private TemplateService templateService;

    @Autowired
    public UnzipController(TemplateService templateService) {
        this.templateService = templateService;
    }
    public static String fileName = "";
    public static String userName = "";
    public static String repositoryName = "";
    static String findURL = "";
    static String findURL_format = "";
    static String content = "";
    @PostMapping(value = "/register")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String jsonParam1,
        @RequestParam("jsonParam2") String jsonParam2, @RequestParam("file") MultipartFile file)
        throws IOException, InterruptedException {
        HashMap<String,Object> map = new HashMap<String,Object>();

        fileName = file.getOriginalFilename();
        userName = jsonParam1;
        repositoryName = jsonParam2;

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

        TemplateDTO templateDTO = templateService.getTemplate("test1");
        String temp=templateDTO.getTemplateContributor();
        temp= temp.replace("repositoryName",repositoryName);
        temp= temp.replace("userName",userName);

        ProcessBuilder builder = new ProcessBuilder();

        // unzipFiles 폴더 생성 - 압축풀기한 파일들을 저장하는 임시 폴더
        builder.command("mkdir", "unzipFiles");
        builder.start();

        File dirFile = new File("./unzipFiles");
        File[] fileList = dirFile.listFiles();

        if(fileList.length != 0){ // 기존에 압축풀기한 파일들이 존재하면 기존 파일들 삭제하고 시작
            System.out.println("기존 파일들 존재! 삭제하고 시작!");
            deleteUnzipFiles(builder);
        }

        // 파일 압축 풀기
        builder.command("unzip", "unzipTest.zip", "-d", "./unzipFiles");
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
        content = searchFiles(searchDirPath); // react response로 보내줄 파일에서 찾은 content

        // content data 보냈으므로, 압축풀기한 파일들, 업로드된 zip 파일 모두 삭제
        deleteUnzipFiles(builder);

        String sample_Data=
            "![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=300&section=header&text=Readme%20Studio&fontSize=90)\n" +
                "<div align=center><h1>\uD83D\uDCDA  STACKS</h1></div>\n" +
                "<div align=center> \n" +
                "  <img src=\"https://img.shields.io/badge/Spring-007396?style=for-the-badge&logo=Spring&logoColor=white\">\n" +
                "  <br>\n" +
                "  \n" +
                "\u200B\n" +
                "</div>\n" +
                "\u200B\n" +
                "## :one: 소개\n" +
                "  이 프로젝트는 Readme Studio 서비스로  Spring Boot로 제작된 프로젝트입니다.\n" +
                "## :two: 패키지 프레임워크 설치\n" +
                "\u200B\n" +
                "<b>clone 프로젝트 </b>\n" +
                "```xml\n" +
                "gh repo clone https://soominkiminsoo/SurveyForm1.git\n" +
                "```\n" +
                " <b>jar 파일 다운로드</b>\n" +
                "* mysql-connector-j-8.0.31.jar\n" +
                "* mail-1.4.7.jar\n" +
                "* activation.jar\n" +
                "   \n" +
                "\u200B\n" +
                "## :three:DB 구조\n" +
                "C:.<br>\n" +
                "├─dao<br>\n" +
                "│    └─Impl<br>\n" +
                "├─dto<br>\n" +
                "├─entity<br>\n" +
                "├─handler<br>\n" +
                "│    └─Impl<br>\n" +
                "├─repository<br>\n" +
                "└─service<br>\n" +
                "       └─Impl<br>\n" +
                "\u200B\n" +
                "## :four:쿼리 메소드\n" +
                "\u200B\n" +
                "## :five:Contributor\n" +
                "\u200B\n" +temp + "\n" +
                "\n<p>User Name : " + userName + "</p>\n" +
                "\n<p>Repository Name : " + repositoryName + "</p>\n" +
                "\n<p>Content : " + content + "</p>\n";
        ;

        map.put("sample_data", sample_Data);
        map.put("contributor", temp);

        return map;
    }

    /* Http 통신 Test
    // http://localhost:8090/readme/url?content=https:%2F%2Fgithub.com%2FYeJi222%2FSpringBoot_Sample_Structure.git
    // url로 넘겨진 content 매개변수 받아오기
    final String getMappingValue = "/url";
    @GetMapping(value = getMappingValue)
    public String getSend(@RequestParam String content){
        return content;
    };
    */


    public static String searchFiles(String searchDirPath) throws IOException {
        File dirFile = new File(searchDirPath);
        File[] fileList = dirFile.listFiles();

        if(fileList.length == 0){ // 압축풀기가 되지 않은 상태
            System.out.println("!!! 압축풀기할 파일이 존재하지 않습니다 !!!");
        } else{
            for(int i = 0 ; i < fileList.length; i++) {
                if(fileList[i].isFile()) {
                    System.out.println(fileList[i].getPath()); // Full Path
                    Scanner reader = new Scanner(new File(fileList[i].getPath()));

                    // 파일 내용들 출력(확인용)
                    /*
                    while (reader.hasNextLine()) {
                        String str = reader.nextLine();
                        System.out.println(str);
                    }
                    */

                    // url 찾아서 파싱
                    while (reader.hasNextLine()) {
                        String str = reader.nextLine();
                        if (str.contains("url=")) {
                            System.out.println("\n[찾는 content]");
                            findURL = str.substring(5, str.length() - 1);
                            System.out.println(findURL);

                            // url로 넘겨질 포맷으로 변경(특수문자 '/' : %2F)
                            findURL_format = findURL.replaceAll("/", "%2F");
                            System.out.println("\n[url 특수문자 형식 변환]");
                            System.out.println(findURL_format);
                            System.out.println();

                            /* uri 매개변수로 content 전송(using HTTP request)
                            URI uri = UriComponentsBuilder
                                .fromUriString("http://localhost:8090")
                                .path("/readme/url")
                                .queryParam("content", findURL_format) // key, value
                                .encode()
                                .build()
                                .toUri();

                            RestTemplate restTemplate = new RestTemplate();
                            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

                            String content = responseEntity.getBody().replaceAll("%2F", "/");
                            System.out.println("[HTTP 전송]");
                            System.out.println("uri : " + uri);
                            System.out.println("status code : " + responseEntity.getStatusCode());
                            System.out.println("content : " + content);
                            */
                        }
                    }
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
        builder.command("rm", "-rf", "./unzipFiles/");
        builder.start();
        builder.command("rm", "-rf", "./unzipTest.zip");
        builder.start();

        System.out.println("업로드된 zip파일, 압축풀기한 파일들 모두 삭제 완료!!");
    }
}
