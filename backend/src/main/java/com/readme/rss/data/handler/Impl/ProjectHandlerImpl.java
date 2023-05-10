package com.readme.rss.data.handler.Impl;

import com.readme.rss.data.dao.ProjectDAO;
import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import com.readme.rss.data.handler.ProjectHandler;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectHandlerImpl implements ProjectHandler {
    ProjectDAO projectDAO;

    @Autowired
    public ProjectHandlerImpl(ProjectDAO projectDAO){
        this.projectDAO = projectDAO;
    }

    @Override
    public void saveProject(String id, String file_name, String file_path, String file_content, String detail){
      ProjectEntity projectEntity = new ProjectEntity(id, file_name, file_path, file_content, detail);
      projectDAO.saveProject(projectEntity);
    }

    @Override
    public ProjectDTO getProject(String id){
      ProjectEntity projectEntity = projectDAO.getProject(id);
      ProjectDTO projectDTO = new ProjectDTO(projectEntity.getId(), projectEntity.getFileName(), projectEntity.getFilePath(), projectEntity.getFileContent(), projectEntity.getDetail());

      return projectDTO;
    }

    @Override
    public List<ProjectDTO> getController(String projectId){
        List<ProjectEntity> result = projectDAO.getController(projectId);
        List<ProjectDTO> projectDTOList = new ArrayList<>();

        for(ProjectEntity temp : result){
          ProjectDTO projectDTO = new ProjectDTO(temp.getId(), temp.getFileName(), temp.getFilePath(), temp.getFileContent(), temp.getDetail());
          projectDTOList.add(projectDTO);
        }

        return projectDTOList;
    }

    @Override
    public List<ProjectDTO> getFileContent(String id){
      List<ProjectEntity> result = projectDAO.getFileContent(id);
      List<ProjectDTO> projectDTOList = new ArrayList<>();

      for(ProjectEntity temp : result){
        ProjectDTO projectDTO = new ProjectDTO(temp.getId(), temp.getFileName(), temp.getFilePath(), temp.getFileContent(), temp.getDetail());
        projectDTOList.add(projectDTO);
      }

      return projectDTOList;
    }

    @Override
    public String getFileContentByFileName(String id, String file_name){
        return projectDAO.getFileContentByFileName(id, file_name);
    }
}