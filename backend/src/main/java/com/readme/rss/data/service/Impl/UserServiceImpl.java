package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.handler.UserHandler;
import com.readme.rss.data.service.UserService;
import java.util.HashMap;
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

    @Override
    public UserDTO getUser(String project_id){
      return userHandler.getUserEntity(project_id);
    }

    @Override
    public UserDTO registerUser(String userName, String repositoryName){
      String randomId = this.createProjectId();
      return userHandler.saveUserEntity(randomId,userName,repositoryName);
    }

  @Override
  public UserDTO registerUserLink(String repoLink){
    UserDTO data = new UserDTO();
    if(!repoLink.contains("https://github.com/")){ // (1) 예외처리
      return new UserDTO();
    }
    if(!repoLink.contains(".git")){ // (2) 예외처리
      repoLink += ".git";
    }

    String randomId = this.createProjectId();

    String repoLinkInfo = repoLink.substring(19);
    String userName = repoLinkInfo.split("/")[0];
    String repositoryName = repoLinkInfo.split("/")[1].substring(0, repoLinkInfo.split("/")[1].indexOf(".git"));

    return userHandler.saveUserEntity(randomId,userName,repositoryName);
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