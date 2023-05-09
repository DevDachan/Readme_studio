// repository : entity에 의해 생성된 db에 접근하는 메소드를 사용하기 위한 인터페이스
// service와 db를 연결하는 고리의 역할을 수행
// 데이터베이스에 적용하고자하는 CRUD를 정의하는 영역
// JpaRepository를 사용할 것 -> db에 접근할 수 있는 기본 메소드를 제공해줌

package com.readme.rss.data.repository;

import com.readme.rss.data.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {
 // <(Repository가 사용할 entity), (entity에서 사용되는 primary key의 데이터 타입)>
}
