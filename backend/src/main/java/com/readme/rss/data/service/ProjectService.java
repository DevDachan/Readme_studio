package com.readme.rss.data.service;

import com.readme.rss.data.dto.ProjectDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ProjectService {
    void saveProject(String id, String file_name, String file_path, String file_content, String detail);

    void saveData(String id, List<String> javaFileName, List<String> javaFilePath, List<String> javaFileContent,
        List<String> javaFileDetail);

    HashMap<String, String> getProjectDetail(String id);

    ProjectDTO getProject(String id);

    List<ProjectDTO> getController(String projectId);


    List<ProjectDTO> getFileContent(String id);

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
