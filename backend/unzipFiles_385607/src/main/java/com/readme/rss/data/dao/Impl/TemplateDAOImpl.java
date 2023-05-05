package com.readme.rss.data.dao.Impl;

import com.readme.rss.data.dao.TemplateDAO;
import com.readme.rss.data.entity.TemplateEntity;
import com.readme.rss.data.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateDAOImpl implements TemplateDAO {
    // DAO에서 repository를 활용해서 코드를 작성하므로 TemplateRepository 사용
    TemplateRepository templateRepository;

    @Autowired // 자동으로 연결시켜 객체를 주입받음
    public TemplateDAOImpl(TemplateRepository templateRepository){ // 생성자
        this.templateRepository = templateRepository;
    }

    // JpaRepository에서 save, getById 같은 기본적인 메소드들을 제공해준다
    @Override
    public TemplateEntity saveTemplate(TemplateEntity templateEntity){
        templateRepository.save(templateEntity); // db에 저장
        return templateEntity;
    }

    @Override
    public TemplateEntity getTemplate(String templateId){
        // templateId 기반으로 데이터를 가져옴
        // templateId를 넘겨주고 templateEntity를 받아온다
        TemplateEntity templateEntity = templateRepository.getById(templateId);
        return templateEntity;
    }
}
