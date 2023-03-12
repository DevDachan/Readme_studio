package com.example.Rss_project.data.handler;

import com.example.Rss_project.data.entity.TemplateEntity;

public interface TemplateHandler {
    TemplateEntity saveTemplateEntity(String templateId, String templateContributor);

    TemplateEntity getTemplateEntity(String templateId);

}
