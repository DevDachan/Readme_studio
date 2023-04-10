package com.readme.rss.data.dao;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;
import java.util.Map;

public interface ProjectDAO {
    ProjectEntity saveProject(ProjectEntity projectEntity);
    ProjectEntity getProject(String id);

    List<ProjectEntity> getController(String projectId);

    List<String> getIdAll();

    List<ProjectEntity> getFileContent(String id);
}
