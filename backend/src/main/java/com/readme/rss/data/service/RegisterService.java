package com.readme.rss.data.service;

import java.io.IOException;
import java.util.HashMap;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

  HashMap<String, Object> registerLink(String repoLink, String projectId) throws IOException;

  HashMap<String, Object> register(String userName, String repositoryName,
      MultipartFile file, String projectId) throws IOException, InterruptedException;

  HashMap<String, Object> parseData(String noWhiteSpaceXml, String propertiesContent);
}
