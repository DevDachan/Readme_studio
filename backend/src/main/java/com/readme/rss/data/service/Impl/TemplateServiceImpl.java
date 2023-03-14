package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.TemplateDTO;
import com.readme.rss.data.entity.TemplateEntity;
import com.readme.rss.data.handler.TemplateHandler;
import com.readme.rss.data.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {
    TemplateHandler templateHandler;

    @Autowired
    public TemplateServiceImpl(TemplateHandler templateHandler){
        this.templateHandler = templateHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public TemplateDTO saveTemplate(String templateId, String templateContributor){
        TemplateEntity templateEntity = templateHandler.saveTemplateEntity(templateId, templateContributor);

        TemplateDTO templateDTO = new TemplateDTO(templateEntity.getTemplateId(), templateEntity.getTemplateContributor());
        return templateDTO;
    }

    @Override
    public TemplateDTO getTemplate(String templateId){
        TemplateEntity templateEntity = templateHandler.getTemplateEntity(templateId);

        TemplateDTO templateDTO = new TemplateDTO(templateEntity.getTemplateId(), templateEntity.getTemplateContributor());
        return templateDTO;
    }

}
