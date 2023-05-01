package com.readme.rss.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3005")
@RestController
// @RequestMapping("/readme")
public class MarkdownController {
    @PostMapping(value = "/mdZipFile")
    public byte[] makeMDzipFile(@RequestBody List<Map<String, Object>> readme) throws IOException { // 받을 인자: md파일 명,
        String project_id = readme.get(0).get("projectId").toString();

        System.out.println();
        System.out.println("project_id : " + project_id);
        System.out.println("readme : " + readme);
        System.out.println("readme size : " + readme.size());
        System.out.println("[Make Markdown Files Function]");

        ProcessBuilder builder = new ProcessBuilder();

        // mdFiles 폴더 생성 - 생성한 md 파일들을 저장하는 임시 폴더
        String mdFilesName = "mdFiles_" + project_id;
        builder.command("mkdir", mdFilesName); // mac
        // builder.command("cmd.exe","/c","mkdir", mdFilesName); // window
        builder.start();

        File mdFile = null;

        for (int i = 0; i < readme.size(); i++) {
            String mdName = readme.get(i).get("id").toString(); // md file name
            ArrayList<String> framework = (ArrayList<String>) readme.get(i).get("content"); // md framework content list
            String content = ""; // md file 하나의 string으로
            for (int j = 0; j < framework.size(); j++) {
                content = content + framework.get(j) + "\n\n";
            }
            System.out.println("test - " + mdName);

            String filePath = "./" + mdFilesName + "/" + mdName; // ex) ./mdFiles_979748/Readme.md
            mdFile = new File(filePath);
            boolean ret = false;
            while(!ret) { // 파일 새로 생성 성공할 때까지
                try {
                    ret = mdFile.createNewFile(); // 파일 새로 생성
                } catch (Exception e) {}
            }

            if(ret){
                mdFile.createNewFile(); // 파일 새로 생성
                BufferedWriter mdWriter = new BufferedWriter(new FileWriter(mdFile, true));

                mdWriter.write("<h1>" + mdName + " File</h1>");
                mdWriter.newLine();
                mdWriter.newLine();
                mdWriter.write(content);
                mdWriter.newLine();

                mdWriter.flush(); // 버퍼의 남은 데이터를 모두 쓰기
                mdWriter.close();
            }
        }

        // md 파일들 압축하기
        String mdZipFilesName = "mdZipFiles_" + project_id + ".zip";
        // builder.directory(new File("./mdFiles")); // mdFiles로 이동
        builder.command("zip", mdZipFilesName, "-r", mdFilesName); // mac
        // builder.command("cmd.exe", "/c", "zip", mdZipFilesName, "-r", mdFilesName); // window

        var process = builder.start(); // zip 실행

        // zip 실행 후, 콘솔에 출력해주기
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                System.out.println(commandResult);
            }
        }

        File mdZipFile = new File(mdZipFilesName);
        byte[] zipResult = Files.readAllBytes(mdZipFile.toPath());

        // md 파일들, md zip한 파일 지우기
        /* mac */
        builder.command("rm", "-rf", mdFilesName);
        builder.start();
        builder.command("rm", "-rf", mdZipFilesName);
        builder.start();

        /* window
        builder.command("cmd.exe","/c","del", mdFilesName);
        builder.start();
        builder.command("cmd.exe","/c","del","/Q", mdZipFilesName);
        builder.start();
        */

        System.out.println("md zip파일, md 파일들 모두 삭제 완료!!");

        return zipResult;
    }

//    @PostMapping(value = "/mdFile")
//    public byte[] makeMDfile(@RequestBody Map<String, Object> readme) throws IOException { // 받을 인자: md파일 명,
//        System.out.println();
//        System.out.println("[Make Markdown Files Function]");
//
//        ProcessBuilder builder = new ProcessBuilder();
//
//        // mdFiles 폴더 생성
//        builder.command("mkdir", "mdFile"); // mac
//        // builder.command("cmd.exe","/c","mkdir", "mdFiles"); // window
//        builder.start();
//
//        File mdFile = null;
//        String mdName = readme.get("id").toString(); // md file name
//        ArrayList<String> framework = (ArrayList<String>) readme.get("content"); // md framework content list
//        String content = ""; // md file 하나의 string으로
//        for (int i = 0; i < framework.size(); i++) {
//            content = content + framework.get(i) + "\n\n";
//        }
//        // System.out.println(mdName + " : " + content);
//        String filePath = "./mdFile/" + mdName; // ex) ./mdFile/A.md
//        mdFile = new File(filePath);
//        mdFile.createNewFile(); // 파일 새로 생성
//
//        BufferedWriter mdWriter = new BufferedWriter(new FileWriter(mdFile, true));
//        mdWriter.write("<h1>" + mdName + " File</h1>");
//        mdWriter.newLine();
//        mdWriter.newLine();
//        mdWriter.write(content);
//        mdWriter.newLine();
//
//        mdWriter.flush(); // 버퍼의 남은 데이터를 모두 쓰기
//        mdWriter.close();
//
//        byte[] resultFile = Files.readAllBytes(mdFile.toPath());
//
//        // md 파일 지우기
//        /* mac */
//        builder.command("rm", "-rf", "./mdFile"); // 파일 삭제
//        builder.start();
//        builder.command("mkdir", "mdFile"); // 폴더 다시 생성해주기
//        builder.start();
//
//        /* window
//        builder.command("cmd.exe","/c","del", "./mdFile");
//        builder.start();
//        builder.command("cmd.exe","/c","mkdir", "mdFiles");
//        builder.start();
//        */
//
//        System.out.println("md 파일 삭제 완료!!");
//
//        return resultFile;
//    }
}