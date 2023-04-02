package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.TemplateDAO;
import com.readme.rss.data.entity.TemplateEntity;
import com.readme.rss.data.handler.TemplateHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TemplateHandlerImpl implements TemplateHandler {
    TemplateDAO templateDAO;

    @Autowired
    public TemplateHandlerImpl(TemplateDAO templateDAO){
        this.templateDAO = templateDAO;
    }

    @Override
    public TemplateEntity saveTemplateEntity(String templateId, String templateContributor){
        TemplateEntity templateEntity = new TemplateEntity(templateId, templateContributor);
        return templateDAO.saveTemplate(templateEntity);
    }

    @Override
    public TemplateEntity getTemplateEntity(String TemplateId){
        return templateDAO.getTemplate(TemplateId);
    }
}
