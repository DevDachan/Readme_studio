package com.readme.rss.data.entity;

import com.readme.rss.data.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "User")
public class UserEntity {
    @Id
    String projectId;
    String userName;
    String repositoryName;

    public UserDTO toDto(){
        return UserDTO.builder()
            .projectId(projectId)
            .userName(userName)
            .repositoryName(repositoryName)
            .build();
    }
}
