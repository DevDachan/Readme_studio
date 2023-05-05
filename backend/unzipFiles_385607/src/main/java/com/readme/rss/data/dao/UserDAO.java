package com.readme.rss.data.dao;

import com.readme.rss.data.entity.UserEntity;

public interface UserDAO {
    UserEntity saveUser(UserEntity userEntity);
    UserEntity getUser (String project_id);
}
