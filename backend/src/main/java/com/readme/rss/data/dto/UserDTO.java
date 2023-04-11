package com.readme.rss.data.dto;

import com.readme.rss.data.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    @NotNull
    private String project_id;
    @NotNull
    private String user_name;
    private String repository_name;

    public UserEntity toEntity(){
        return UserEntity.builder()
            .project_id(project_id)
            .user_name(user_name)
            .repository_name(repository_name)
            .build();
    }
}

