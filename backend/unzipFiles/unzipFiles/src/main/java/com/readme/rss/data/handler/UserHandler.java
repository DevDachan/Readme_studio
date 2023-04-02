package com.readme.rss.data.handler;

import com.readme.rss.data.entity.UserEntity;

public interface UserHandler {
    UserEntity saveUserEntity(String project_id, String user_name, String repository_name);

    UserEntity getUserEntity(String project_id);

}
