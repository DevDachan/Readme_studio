package com.readme.rss.data.entity;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.compositeKey.ProjectPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Project")
@IdClass(ProjectPK.class)
public class ProjectEntity {
    @Id
    String id;
    @Id
    String fileName;
    @Id
    String filePath;
    String fileContent;
    String detail;
    public ProjectDTO toDto(){
        return ProjectDTO.builder()
            .id(id)
            .fileName(fileName)
            .filePath(filePath)
            .fileContent(fileContent)
            .build();
    }
}