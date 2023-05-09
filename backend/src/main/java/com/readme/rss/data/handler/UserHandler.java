package com.readme.rss.data.handler;

import com.readme.rss.data.dto.UserDTO;
import java.util.List;

public interface UserHandler {
    UserDTO saveUserEntity(String project_id, String user_name, String repository_name);

    UserDTO getUserEntity(String project_id);
    List<String> getIdAll();
}
