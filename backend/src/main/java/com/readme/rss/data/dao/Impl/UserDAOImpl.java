package com.readme.rss.data.dao.Impl;

import com.readme.rss.data.dao.UserDAO;
import com.readme.rss.data.entity.UserEntity;
import com.readme.rss.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAOImpl implements UserDAO {
    UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getUser(String project_id){
        UserEntity userEntity = userRepository.getReferenceById(project_id);
        return userEntity;
    }
}