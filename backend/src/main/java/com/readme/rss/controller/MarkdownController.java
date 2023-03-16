package com.readme.rss.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3005")
@RestController
// @RequestMapping("/readme")
public class MarkdownController {
    @PostMapping(value = "/markdown")
    public byte[] makeMDfile(@RequestParam("mdName") String mdName,
        @RequestParam("userName") String userName, @RequestParam("repName") String repName) throws IOException { // 받을 인자: md파일 명,
        System.out.println();
        System.out.println("[Make Markdown Function]");
        System.out.println("mdName : " + mdName);
        System.out.println("userName : " + userName);
        System.out.println("repName : " + repName);

        /* mdFiles 폴더를 미리 만들어져있는 것도 괜찮을 듯
        ProcessBuilder builder = new ProcessBuilder();

        // mdFiles 폴더 생성 - 생성한 md 파일들을 저장하는 임시 폴더
        builder.command("mkdir", "mdFiles");
        builder.start();
         */

        String filePath = "./mdFiles/" + mdName + ".md"; // ex) ./mdFiles/A.md

        File mdFile = new File(filePath);
        if(!mdFile.exists()){ // 기존에 해당 이름의 md파일이 존재하지 않으면
            mdFile.createNewFile(); // 파일 새로 생성
        }

        BufferedWriter mdWriter = new BufferedWriter(new FileWriter(mdFile, true));
        mdWriter.write("<h1>" + mdName + ".md File</h1>");
        mdWriter.newLine();
        mdWriter.write("<a href=\"https://github.com/" + userName + "/" + repName + "/graphs/contributors\">\n"
            + "  <img src=\"https://contrib.rocks/image?repo=" + userName + "/" + repName + "\" /> </a>");
        mdWriter.newLine();

        mdWriter.flush(); // 버퍼의 남은 데이터를 모두 쓰기
        mdWriter.close();

        byte[] byteFile = Files.readAllBytes(mdFile.toPath());

        System.out.println(byteFile);

        return byteFile;
    }
}
