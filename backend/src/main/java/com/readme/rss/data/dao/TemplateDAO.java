package com.readme.rss.data.dao;

import com.readme.rss.data.entity.TemplateEntity;

public interface TemplateDAO {
    TemplateEntity saveTemplate(TemplateEntity templateEntity);
    TemplateEntity getTemplate (String templateId);
}
