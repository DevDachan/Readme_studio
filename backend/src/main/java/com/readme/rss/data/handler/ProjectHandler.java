package com.readme.rss.data.handler;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProjectHandler {
    ProjectEntity saveProjectEntity(String id, String file_name, String file_path, String  file_content, String detail);
    ProjectEntity getProjectEntity(String id);

    List<ProjectEntity> getController(String projectId);

    List<String> getIdAll();

    List<ProjectEntity> getFileContent(String id);
}
