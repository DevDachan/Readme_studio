package com.readme.rss.data.repository;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
    // @Query 사용
    @Query(value = "SELECT DISTINCT id FROM project", nativeQuery = true)
    List<String> findDistinctId();
}
