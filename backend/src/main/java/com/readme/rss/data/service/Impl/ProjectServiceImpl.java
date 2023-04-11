package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import com.readme.rss.data.handler.ProjectHandler;
import com.readme.rss.data.service.ProjectService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectHandler projectHandler;

    @Autowired
    public ProjectServiceImpl(ProjectHandler projectHandler){
        this.projectHandler = projectHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public ProjectDTO saveProject(String id, String file_name, String file_path, String file_content, String detail){
        ProjectEntity projectEntity = projectHandler.saveProjectEntity(id, file_name, file_path, file_content, detail);

        ProjectDTO projectDTO = new ProjectDTO(projectEntity.getId(), projectEntity.getFile_name(), projectEntity.getFile_path(),projectEntity.getFile_content(), projectEntity.getDetail());
        return projectDTO;
    }

    @Override
    public ProjectDTO getProject(String id){
        ProjectEntity projectEntity = projectHandler.getProjectEntity(id);

        ProjectDTO projectDTO = new ProjectDTO(projectEntity.getId(), projectEntity.getFile_name(), projectEntity.getFile_path(), projectEntity.getFile_content(), projectEntity.getDetail());
        return projectDTO;
    }

    @Override
    public List<ProjectEntity>  getController(String projectId){
        List<ProjectEntity> result = projectHandler.getController(projectId);
        return result;
    }

    @Override
    public List<String> getIdAll(){
        return projectHandler.getIdAll();
    }

    @Override
    public List<ProjectEntity> getFileContent(String id){
        return projectHandler.getFileContent(id);
    }
}
