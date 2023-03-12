package com.example.Rss_project.data.service;

import com.example.Rss_project.data.dto.TemplateDTO;

public interface TemplateService {
    TemplateDTO saveTemplate(String templateId, String templateContributor);

    TemplateDTO getTemplate(String templateId);
}
