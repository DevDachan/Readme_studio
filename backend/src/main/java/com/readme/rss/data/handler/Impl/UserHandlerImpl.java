package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.UserDAO;
import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public UserDTO saveUserEntity(String project_id, String user_name, String repository_name){
      UserEntity userEntity = new UserEntity(project_id, user_name, repository_name);
      userEntity = userDAO.saveUser(userEntity);
      UserDTO user = new UserDTO(userEntity.getProjectId(), userEntity.getUserName(), userEntity.getRepositoryName());

      return user;
    }
    @Override
    public List<String> getIdAll(){
        return userDAO.getIdAll();
    }

    @Override
    public UserDTO getUserEntity(String project_id){
      UserEntity userEntity = userDAO.getUser(project_id);
      UserDTO user = new UserDTO(userEntity.getProjectId(), userEntity.getUserName(), userEntity.getRepositoryName());
      return user;
    }
}