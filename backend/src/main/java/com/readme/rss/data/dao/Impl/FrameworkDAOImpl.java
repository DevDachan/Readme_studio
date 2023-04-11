package com.readme.rss.data.dao.Impl;

import com.readme.rss.data.dao.FrameworkDAO;
import com.readme.rss.data.entity.FrameworkEntity;
import com.readme.rss.data.repository.FrameworkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrameworkDAOImpl implements FrameworkDAO {
    FrameworkRepository frameworkRepository;

    @Autowired
    public FrameworkDAOImpl(FrameworkRepository frameworkRepository){
        this.frameworkRepository = frameworkRepository;
    }

    @Override
    public FrameworkEntity saveFramework(FrameworkEntity frameworkEntity){
        frameworkRepository.save(frameworkEntity);
        return frameworkEntity;
    }

    @Override
    public FrameworkEntity getFramework(String name){
        FrameworkEntity frameworkEntity = frameworkRepository.getReferenceById(name);
        return frameworkEntity;
    }

    @Override
    public List<String> getFrameworkNameList(){
        return frameworkRepository.findAllName();
    }

    @Override
    public String findContent(String name){
        return frameworkRepository.findByName(name).getContent();
    }

}