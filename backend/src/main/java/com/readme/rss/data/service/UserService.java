package com.readme.rss.data.service;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    UserDTO getUser(String project_id);

    String createProjectId();

    UserDTO registerUser(String userName, String repositoryName);
    UserDTO registerUserLink(String repoLink);
}
