package com.readme.rss.data.dto;

import com.readme.rss.data.entity.FrameworkEntity;
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
public class FrameworkDTO {
    @NotNull
    private String type;
    @NotNull
    private String name;
    private String content;

    public FrameworkEntity toEntity(){
        return FrameworkEntity.builder()
            .type(type)
            .name(name)
            .content(content)
            .build();

    }
}