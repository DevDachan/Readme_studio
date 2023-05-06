package com.readme.rss.data.service;

import com.readme.rss.data.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO saveUser(String project_id, String user_name, String repository_name);

    UserDTO getUser(String project_id);

    String createProjectId();
}
