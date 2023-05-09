package com.readme.rss.data.handler;

import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;

public interface ProjectHandler {
    ProjectEntity saveProjectEntity(String id, String file_name, String file_path, String  file_content, String detail);
    ProjectEntity getProjectEntity(String id);

    List<ProjectEntity> getController(String projectId);


    List<ProjectEntity> getFileContent(String id);

    String getFileContentByFileName(String id, String file_name);

}
