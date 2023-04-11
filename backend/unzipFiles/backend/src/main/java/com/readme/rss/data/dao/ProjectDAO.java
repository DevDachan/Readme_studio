package com.readme.rss.data.dao;

import com.readme.rss.data.entity.ProjectEntity;

public interface ProjectDAO {
    ProjectEntity saveProject(ProjectEntity projectEntity);
    ProjectEntity getProject(String id);
}
