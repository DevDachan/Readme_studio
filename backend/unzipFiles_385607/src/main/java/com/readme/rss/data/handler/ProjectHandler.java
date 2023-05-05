package com.readme.rss.data.handler;

import com.readme.rss.data.entity.ProjectEntity;

public interface ProjectHandler {
    ProjectEntity saveProjectEntity(String id, String file_name, String file_path, String  file_content);
    ProjectEntity getProjectEntity(String id);
}
