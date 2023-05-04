package com.readme.rss.data.service;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import java.io.IOException;
import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(String id, String file_name, String file_path, String file_content, String detail);
    ProjectDTO getProject(String id);

    List<ProjectEntity> getController(String projectId);

    List<String> getIdAll();

    List<ProjectEntity> getFileContent(String id);
    String getFileContentByFileName(String id, String file_name);

    String getWebAPI(String projectId);
    String getSocial(String social_temp, String user_name) throws IOException;
    String getContributor(String framework, String repo_name, String user_name);

    String getHeader(String framework, String repo_name);
}
