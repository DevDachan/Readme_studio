package com.readme.rss.controller;

import com.readme.rss.data.service.MdDownloadService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MarkdownController {
    private MdDownloadService mdDownloadService;

    @Autowired
    public MarkdownController(MdDownloadService mdDownloadService) {
        this.mdDownloadService = mdDownloadService;
    }
    @PostMapping(value = "/mdZipFile")
    public byte[] makeMDzipFile(@RequestBody List<Map<String, Object>> readme) throws IOException {
      String projectId = readme.get(0).get("projectId").toString();
      String mdFilesName = "mdFiles_" + projectId;

      // md 파일들 생성하여 담을 디렉토리 생성
      mdDownloadService.makeMdDirectory(projectId, mdFilesName, readme);

      // md 파일들 생성 및 내용 작성
      mdDownloadService.writeMdContents(mdFilesName, readme);

      // md 파일들 압축
      byte[] zipResult = mdDownloadService.zipMdFiles(mdFilesName);

      // md 디렉토리 삭제
      mdDownloadService.deleteMdDirectory(mdFilesName);

      return zipResult;
    }
}