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
    String file_name;
    @Id
    String file_path;
    String file_content;
    String detail;
    public ProjectDTO toDto(){
        return ProjectDTO.builder()
            .id(id)
            .file_name(file_name)
            .file_path(file_path)
            .file_content(file_content)
            .file_content(file_content)
            .build();
    }
}