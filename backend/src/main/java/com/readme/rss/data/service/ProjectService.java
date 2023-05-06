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

    /* API */
    String getArchitecture(String id, String fileName);
    String getWebAPI(String projectId);
    String getSocial(String socialTemp, String userName) throws IOException;
    String getContributor(String framework, String repoName, String userName);
    String getHeader(String framework, String repoName);
    String getPeriod(String framework);
    String getDBTable(String projectId);
    String getLicense(String projectId, String userName);

    String getDependency(String projectId, String userName);

}
