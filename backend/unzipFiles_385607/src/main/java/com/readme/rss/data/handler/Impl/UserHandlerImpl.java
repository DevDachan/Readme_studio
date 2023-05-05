package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.UserDAO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserHandlerImpl implements UserHandler {
    UserDAO userDAO;

    @Autowired
    public UserHandlerImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public UserEntity saveUserEntity(String project_id, String user_name, String repository_name){
        UserEntity userEntity = new UserEntity(project_id, user_name, repository_name);
        return userDAO.saveUser(userEntity);
    }

    @Override
    public UserEntity getUserEntity(String project_id){
        return userDAO.getUser(project_id);
    }
}