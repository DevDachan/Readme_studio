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
    private String projectId;
    @NotNull
    private String userName;
    private String repositoryName;

    public UserEntity toEntity(){
        return UserEntity.builder()
            .projectId(projectId)
            .userName(userName)
            .repositoryName(repositoryName)
            .build();
    }
}

