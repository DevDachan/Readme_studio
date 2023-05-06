package com.readme.rss.data.dao;

import com.readme.rss.data.entity.UserEntity;
import java.util.List;

public interface UserDAO {
    UserEntity saveUser(UserEntity userEntity);
    UserEntity getUser (String projectId);

    List<String> getIdAll();
}
