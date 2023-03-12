package com.example.Rss_project.data.dao;

import com.example.Rss_project.data.entity.TemplateEntity;
public interface TemplateDAO {
    TemplateEntity saveTemplate(TemplateEntity templateEntity);
    TemplateEntity getTemplate (String templateId);
}
