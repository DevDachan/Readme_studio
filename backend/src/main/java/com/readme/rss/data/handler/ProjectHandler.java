package com.readme.rss.data.handler;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import java.util.List;

public interface ProjectHandler {
    void saveProject(String id, String file_name, String file_path, String  file_content, String detail);
    ProjectDTO getProject(String id);

    List<ProjectDTO> getController(String projectId);


    List<ProjectDTO> getFileContent(String id);

    String getFileContentByFileName(String id, String file_name);

}
