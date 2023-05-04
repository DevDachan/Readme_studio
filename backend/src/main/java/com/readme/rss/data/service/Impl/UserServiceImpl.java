package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import com.readme.rss.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserHandler userHandler;

    @Autowired
    public UserServiceImpl(UserHandler userHandler){
        this.userHandler = userHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public UserDTO saveUser(String project_id, String user_name, String repository_name){
        UserEntity userEntity = userHandler.saveUserEntity(project_id, user_name, repository_name);

        UserDTO userDTO = new UserDTO(userEntity.getProjectId(), userEntity.getUserName(), userEntity.getRepositoryName());
        return userDTO;
    }

    @Override
    public UserDTO getUser(String project_id){
        UserEntity userEntity = userHandler.getUserEntity(project_id);

        UserDTO userDTO = new UserDTO(userEntity.getProjectId(), userEntity.getUserName(), userEntity.getRepositoryName());
        return userDTO;
    }
}