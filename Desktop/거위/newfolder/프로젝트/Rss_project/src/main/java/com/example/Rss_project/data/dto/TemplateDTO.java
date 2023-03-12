package com.example.Rss_project.data.dto;
import com.example.Rss_project.data.entity.TemplateEntity;
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
    private String templateName;
    @NotNull
    @Min(value = 500)
    @Max(value = 30000)
    private int templatePrice;
    @NotNull
    @Min(value = 0)
    @Max(value = 9999)
    private int templateStock;

    public TemplateEntity toEntity(){
        return TemplateEntity.builder()
                .templateId(templateId)
                .templateName(templateName)
                .templatePrice(templatePrice)
                .templateStocks(templateStock)
                .build();

    }
}