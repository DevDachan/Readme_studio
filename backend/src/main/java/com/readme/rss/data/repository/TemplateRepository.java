package com.readme.rss.data.repository;

import com.readme.rss.data.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {

}
