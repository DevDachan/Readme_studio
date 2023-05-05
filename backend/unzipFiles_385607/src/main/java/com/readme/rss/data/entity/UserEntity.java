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
//    @Id
//    @JoinColumn(name = "project_id", referencedColumnName = "id") // fk 설정할 때 사용
//    ProjectEntity project_id;

    @Id
    String project_id;
    String user_name;
    String repository_name;

    public UserDTO toDto(){
        return UserDTO.builder()
            .project_id(project_id)
            .user_name(user_name)
            .repository_name(repository_name)
            .build();
    }
}
