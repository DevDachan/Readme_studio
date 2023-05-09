package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import com.readme.rss.data.service.UserService;
import java.util.List;
import java.util.Random;
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

    @Override
    public String createProjectId(){ // random한 projectId 생성하는 함수
        int tempRandomId = 0;
        List<String> randomIdList = userHandler.getIdAll();

        int min = 100000, max = 999999;
        Random random = new Random();
        random.setSeed(System.nanoTime());

        for(int i = 0 ; ; i++){
            tempRandomId = random.nextInt((max - min) + min);
            if(randomIdList.indexOf(tempRandomId) == -1){ // idList에 없는 랜덤 id가 결정되면
                randomIdList.add(String.valueOf(tempRandomId));
                break;
            }
        }
        String randomId = Integer.toString(tempRandomId);

        return randomId;
    }

}