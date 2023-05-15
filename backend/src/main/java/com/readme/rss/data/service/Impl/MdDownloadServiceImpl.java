package com.readme.rss.data.service.Impl;

import com.readme.rss.data.service.MdDownloadService;
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
import org.springframework.stereotype.Service;

@Service
public class MdDownloadServiceImpl implements MdDownloadService {

    public void makeMdDirectory(String projectId, String mdFilesName, List<Map<String, Object>> readme) throws IOException{
        // mdFiles 폴더 생성 - 생성한 md 파일들을 저장하는 임시 폴더
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("mkdir", mdFilesName); // mac
        // builder.command("cmd.exe","/c","mkdir", mdFilesName); // window
        builder.start();

        builder.command("mkdir", "./" + mdFilesName + "/mdFiles"); // mac
        // builder.command("cmd.exe","/c","mkdir", "./" + mdFilesName + "/mdFiles"); // window
        builder.start();
    }

    public void writeMdContents(String mdFilesName, List<Map<String, Object>> readme) throws IOException {
        File mdFile;
        for (int i = 0; i < readme.size(); i++) {
            String mdName = readme.get(i).get("id").toString(); // md file name
            ArrayList<String> framework = (ArrayList<String>) readme.get(i).get("content"); // md framework content list
            String content = ""; // md file 하나의 string으로
            for (int j = 0; j < framework.size(); j++) {
                content = content + framework.get(j) + "\n\n";
            }

            String filePath = "./" + mdFilesName + "/mdFiles/" + mdName; // ex) ./mdFiles_979748/mdFiles/Readme.md
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
    }

    public byte[] zipMdFiles(String mdFilesName) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        //builder.command("pwd");
        //builder.start();

        /*builder.directory(new File(mdFilesName)); // 현재 위치 이동
        builder.start();*/

        String mdZipFilesName = "./mdZipFiles.zip";
        builder.command("zip", mdZipFilesName, "-r", "mdFiles"); // mac
        // builder.command("cmd.exe", "/c", "zip", mdZipFilesName, "-r", "mdFiles"); // window

        var process = builder.start(); // zip 실행
        try (var reader = new BufferedReader( // zip 실행 후, 콘솔에 출력해주기
            new InputStreamReader(process.getInputStream()))) {
            String commandResult;
            while ((commandResult = reader.readLine()) != null) {
                System.out.println(commandResult);
            }
        }

        File mdZipFile = new File(mdFilesName + "/mdZipFiles.zip");
        byte[] zipResult = Files.readAllBytes(mdZipFile.toPath());

        return zipResult;
    }

    public void deleteMdDirectory(String mdFilesName) throws IOException{
        ProcessBuilder builder = new ProcessBuilder();

        try{ // md 파일들, md zip한 파일 지우기
            /* mac*/
            builder.command("rm", "-rf", mdFilesName);
            builder.start();

            /* window
            builder.command("cmd.exe","/c","del", mdFilesName);
            builder.start();*/

            System.out.println("프로젝트별 md file들 삭제 완료!!");
        } catch (IOException e){
            System.out.println("프로젝트별 md file들 삭제 실패");
        }
    }
}
