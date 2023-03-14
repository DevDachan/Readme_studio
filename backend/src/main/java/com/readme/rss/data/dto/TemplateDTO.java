package com.readme.rss.data.dto;
import com.readme.rss.data.entity.TemplateEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TemplateDTO {
    @NotNull
    private String templateId;
    @NotNull
    private String templateContributor;


    public TemplateEntity toEntity(){
        return TemplateEntity.builder()
                .templateId(templateId)
                .templateContributor(templateContributor)
                .build();

    }
}