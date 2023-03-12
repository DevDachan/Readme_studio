package com.example.Rss_project.data.handler;

import com.example.Rss_project.data.entity.TemplateEntity;

public interface TemplateHandler {
    TemplateEntity saveTemplateEntity(String templateId, String templateName, int templatePrice, int templateStock);

    TemplateEntity getTemplateEntity(String templateId);

}
