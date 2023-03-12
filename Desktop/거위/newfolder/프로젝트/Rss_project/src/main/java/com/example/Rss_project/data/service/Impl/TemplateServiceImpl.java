package com.example.Rss_project.data.service.Impl;

import com.example.Rss_project.data.dto.TemplateDTO;
import com.example.Rss_project.data.entity.TemplateEntity;
import com.example.Rss_project.data.handler.TemplateHandler;
import com.example.Rss_project.data.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class TemplateServiceImpl implements TemplateService {
    TemplateHandler templateHandeler;

    @Autowired
    public TemplateServiceImpl(TemplateHandler templateHandler){
        this.templateHandeler = templateHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public TemplateDTO saveTemplate(String templateId, String templateContributor){
        TemplateEntity templateEntity = templateHandeler.saveTemplateEntity(templateId, templateContributor);

        TemplateDTO templateDTO = new TemplateDTO(templateEntity.getTemplateId(), templateEntity.getTemplateContributor());
        return templateDTO;
    }

    @Override
    public TemplateDTO getTemplate(String templateId){
        TemplateEntity templateEntity = templateHandeler.getTemplateEntity(templateId);

        TemplateDTO templateDTO = new TemplateDTO(templateEntity.getTemplateId(), templateEntity.getTemplateContributor());
        return templateDTO;
    }

}
