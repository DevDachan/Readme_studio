// Entity : database table에 1:1로 매핑되는 db와 가장 가까운 객체
// entity 객체를 만든 후, dao에 보낸다
// dao는 db와 직접 통신하는 영역이므로, entity 값을 저장하거나 가져오는 작업들을 수행

// db의 테이블을 하나의 entity로 생각해도 ok
// 이 클래스의 필드는 각 테이블 내부의 컬럼을 의미

package com.readme.rss.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Template") // entity 기반으로 database에 table을 자동으로 생성
public class TemplateEntity {
    @Id // pk
    String templateId;
    String templateContributor;


    public TemplateDTO toDto(){
        return TemplateDTO.builder()
                .templateId(templateId)
                .templateContributor(templateContributor)
                .build();
    }
}
