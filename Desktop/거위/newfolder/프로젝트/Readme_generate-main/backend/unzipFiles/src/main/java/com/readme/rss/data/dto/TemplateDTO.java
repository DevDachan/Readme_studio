// DTO : 계층간 데이터 교환을 위한 객체를 의미
// database table의 컬럼과는 독립적이다

package com.readme.rss.data.dto;
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