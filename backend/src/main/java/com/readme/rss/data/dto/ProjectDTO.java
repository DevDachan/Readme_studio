package com.readme.rss.data.dto;

import com.readme.rss.data.entity.ProjectEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProjectDTO {
    String id;
    String fileName;
    String filePath;
    String fileContent;
    String detail;

    public ProjectEntity toEntity(){
        return ProjectEntity.builder()
            .id(id)
            .fileName(fileName)
            .filePath(filePath)
            .fileContent(fileContent)
            .detail(detail)
            .build();
    }
}