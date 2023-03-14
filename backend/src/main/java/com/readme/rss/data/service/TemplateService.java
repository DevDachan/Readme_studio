package com.readme.rss.data.service;

import com.readme.rss.data.dto.TemplateDTO;

public interface TemplateService {
    TemplateDTO saveTemplate(String templateId, String templateContributor);

    TemplateDTO getTemplate(String templateId);
}
