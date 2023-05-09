package com.readme.rss.data.service;

import java.util.HashMap;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

  HashMap<String, Object> registerLink(String repoLink);

  HashMap<String, Object> register(String userName, String repositoryName,
      MultipartFile file);

}
