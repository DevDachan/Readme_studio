package com.readme.rss.data.repository;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
    @Query(value = "SELECT DISTINCT id FROM project", nativeQuery = true)
    List<String> getIdAll();

    @Query(value = "SELECT * FROM project WHERE id = :id_value", nativeQuery = true)
    List<ProjectEntity> getFileContent(@Param("id_value")String idValue);

    @Query(value = "SELECT * FROM project WHERE id=:projectId AND file_name LIKE('%Controller%')", nativeQuery = true)
    List<ProjectEntity> getController(@Param("projectId")String projectId);

}
