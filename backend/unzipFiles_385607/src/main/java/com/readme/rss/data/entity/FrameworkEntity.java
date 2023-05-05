package com.readme.rss.data.entity;

import com.readme.rss.data.dto.FrameworkDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "Framework")
public class FrameworkEntity {
    @Id
    String name;
    String type;
    String content;

    public FrameworkDTO toDto(){
        return FrameworkDTO.builder()
            .type(type)
            .name(name)
            .content(content)
            .build();
    }
}