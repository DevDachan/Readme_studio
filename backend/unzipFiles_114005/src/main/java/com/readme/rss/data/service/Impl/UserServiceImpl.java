package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import com.readme.rss.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserHandler userHandeler;

    @Autowired
    public UserServiceImpl(UserHandler userHandler){
        this.userHandeler = userHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public UserDTO saveUser(String project_id, String user_name, String repository_name){
        UserEntity userEntity = userHandeler.saveUserEntity(project_id, user_name, repository_name);

        UserDTO userDTO = new UserDTO(userEntity.getProject_id(), userEntity.getUser_name(), userEntity.getRepository_name());
        return userDTO;
    }

    @Override
    public UserDTO getUser(String project_id){
        UserEntity userEntity = userHandeler.getUserEntity(project_id);

        UserDTO userDTO = new UserDTO(userEntity.getProject_id(), userEntity.getUser_name(), userEntity.getRepository_name());
        return userDTO;
    }
///check
}
