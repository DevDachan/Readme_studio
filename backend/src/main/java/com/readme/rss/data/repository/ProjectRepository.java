package com.readme.rss.data.repository;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
    // @Query 사용
    @Query(value = "SELECT DISTINCT id FROM project", nativeQuery = true)
    List<String> findDistinctId();

//    @Query(value = "SELECT file_content FROM project WHERE id = :id_value AND file_path = :path_value", nativeQuery = true)
//    String findFileContent(@Param("id_value")String idValue, @Param("path_value")String pathValue);

    @Query(value = "SELECT * FROM project WHERE id = :id_value", nativeQuery = true)
    List<ProjectEntity> findFileContent(@Param("id_value")String idValue);

    @Query(value = "SELECT * FROM project WHERE id=:projectId AND file_name LIKE('%Controller%')", nativeQuery = true)
    List<ProjectEntity> getController(@Param("projectId")int projectId);
}
