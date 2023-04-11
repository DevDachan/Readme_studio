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
    String file_name;
    String file_path;
    String file_content;
    String detail;

    public ProjectEntity toEntity(){
        return ProjectEntity.builder()
            .id(id)
            .file_name(file_name)
            .file_path(file_path)
            .file_content(file_content)
            .detail(detail)
            .build();
    }
}