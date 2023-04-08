package com.readme.rss.data.service;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProjectService {
    ProjectDTO saveProject(String id, String file_name, String file_path, String file_content, String detail);
    ProjectDTO getProject(String id);

    List<ProjectEntity> getController(int projectId);
}
