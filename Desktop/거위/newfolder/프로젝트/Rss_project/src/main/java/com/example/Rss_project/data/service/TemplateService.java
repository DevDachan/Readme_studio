package com.example.Rss_project.data.service;

import com.example.Rss_project.data.dto.TemplateDTO;

public interface TemplateService {
    TemplateDTO saveTemplate(String templateId, String templateName, int templatePrice, int templateStock);

    TemplateDTO getTemplate(String templateId);
}
