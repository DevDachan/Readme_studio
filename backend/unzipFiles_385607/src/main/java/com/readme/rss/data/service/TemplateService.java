// service : service 객체에서 DTO에 들어있는 data값들을 이용해서
// 내용을 조금 더 붙이거나 빼서 entity 객체를 만든다

package com.readme.rss.data.service;

import com.readme.rss.data.dto.TemplateDTO;

public interface TemplateService {
    TemplateDTO saveTemplate(String templateId, String templateContributor);

    TemplateDTO getTemplate(String templateId);
}
