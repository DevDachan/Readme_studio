package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.FrameworkDAO;
import com.readme.rss.data.dto.FrameworkDTO;
import com.readme.rss.data.entity.FrameworkEntity;
import com.readme.rss.data.handler.FrameworkHandler;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public void saveFramework(String type, String name, String content){
      FrameworkEntity frameworkEntity = new FrameworkEntity(type, name, content);
      frameworkDAO.saveFramework(frameworkEntity);
    }

    @Override
    public FrameworkDTO getFramework(String name){
      FrameworkEntity result = frameworkDAO.getFramework(name);
      FrameworkDTO frameworkDTO = new FrameworkDTO(result.getType(), result.getName(), result.getContent());
      return frameworkDTO;
    }


    @Override
    public List<String> getFrameworkNameList(){
        return frameworkDAO.getFrameworkNameList();
    }

    @Override
    public String findContent(String name){
        return frameworkDAO.findContent(name);
    }
}