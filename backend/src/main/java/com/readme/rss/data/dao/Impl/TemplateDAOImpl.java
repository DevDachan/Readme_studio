package com.readme.rss.data.dao.Impl;

import com.readme.rss.data.dao.TemplateDAO;
import com.readme.rss.data.entity.TemplateEntity;
import com.readme.rss.data.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateDAOImpl implements TemplateDAO {
    TemplateRepository templateRepository;

    @Autowired
    public TemplateDAOImpl(TemplateRepository templateRepository){
        this.templateRepository = templateRepository;
    }

    @Override
    public TemplateEntity saveTemplate(TemplateEntity templateEntity){
        templateRepository.save(templateEntity);
        return templateEntity;
    }

    @Override
    public TemplateEntity getTemplate(String templateId){
        TemplateEntity templateEntity = templateRepository.getById(templateId);
        return templateEntity;
    }
}
