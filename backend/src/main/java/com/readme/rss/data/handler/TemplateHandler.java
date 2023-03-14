package com.readme.rss.data.handler;

import com.readme.rss.data.entity.TemplateEntity;

public interface TemplateHandler {
    TemplateEntity saveTemplateEntity(String templateId, String templateContributor);

    TemplateEntity getTemplateEntity(String templateId);

}
