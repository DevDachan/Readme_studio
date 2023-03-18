package com.readme.rss.data.dao.Impl;

import com.readme.rss.data.dao.ProjectDAO;
import com.readme.rss.data.entity.ProjectEntity;
import com.readme.rss.data.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectDAOImpl implements ProjectDAO {
    ProjectRepository projectRepository;

    @Autowired
    public ProjectDAOImpl(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectEntity saveProject(ProjectEntity projectEntity){
        projectRepository.save(projectEntity);
        return projectEntity;
    }

    @Override
    public ProjectEntity getProject(String id){
        ProjectEntity projectEntity = projectRepository.getReferenceById(id);
        return projectEntity;
    }
}
