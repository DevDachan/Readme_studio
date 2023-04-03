package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.FrameworkDTO;
import com.readme.rss.data.entity.FrameworkEntity;
import com.readme.rss.data.handler.FrameworkHandler;
import com.readme.rss.data.service.FrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrameworkServiceImpl implements FrameworkService {
    FrameworkHandler frameworkHandeler;

    @Autowired
    public FrameworkServiceImpl(FrameworkHandler frameworkHandler){
        this.frameworkHandeler = frameworkHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public FrameworkDTO saveFramework(String id, String name, String content){
        FrameworkEntity frameworkEntity = frameworkHandeler.saveFrameworkEntity(id, name, content);

        FrameworkDTO frameworkDTO = new FrameworkDTO(frameworkEntity.getId(), frameworkEntity.getName(), frameworkEntity.getContent());
        return frameworkDTO;
    }

    @Override
    public FrameworkDTO getFramework(String id){
        FrameworkEntity frameworkEntity = frameworkHandeler.getFrameworkEntity(id);

        FrameworkDTO frameworkDTO = new FrameworkDTO(frameworkEntity.getId(), frameworkEntity.getName(), frameworkEntity.getContent());
        return frameworkDTO;
    }

}
