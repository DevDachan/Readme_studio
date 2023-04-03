package com.readme.rss.data.service;

import com.readme.rss.data.dto.ProjectDTO;

public interface ProjectService {
    ProjectDTO saveProject(String id, String file_name, String file_path, String file_content, String detail);
    ProjectDTO getProject(String id);
}
