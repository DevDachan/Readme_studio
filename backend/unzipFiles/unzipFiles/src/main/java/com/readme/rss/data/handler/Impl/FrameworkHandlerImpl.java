package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.FrameworkDAO;
import com.readme.rss.data.entity.FrameworkEntity;
import com.readme.rss.data.handler.FrameworkHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FrameworkHandlerImpl implements FrameworkHandler {
    FrameworkDAO frameworkDAO;

    @Autowired
    public FrameworkHandlerImpl(FrameworkDAO frameworkDAO){
        this.frameworkDAO = frameworkDAO;
    }

    @Override
    public FrameworkEntity saveFrameworkEntity(String type, String name, String content){
        FrameworkEntity frameworkEntity = new FrameworkEntity(type, name, content);
        return frameworkDAO.saveFramework(frameworkEntity);
    }

    @Override
    public FrameworkEntity getFrameworkEntity(String name){
        return frameworkDAO.getFramework(name);
    }
}