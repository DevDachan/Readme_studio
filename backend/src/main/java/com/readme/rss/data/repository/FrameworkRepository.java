// repository : entity에 의해 생성된 db에 접근하는 메소드를 사용하기 위한 인터페이스
// service와 db를 연결하는 고리의 역할을 수행
// 데이터베이스에 적용하고자하는 CRUD를 정의하는 영역
// JpaRepository를 사용할 것 -> db에 접근할 수 있는 기본 메소드를 제공해줌

package com.readme.rss.data.repository;

import com.readme.rss.data.entity.FrameworkEntity;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface FrameworkRepository extends JpaRepository<FrameworkEntity, String> {
    @Query(value = "SELECT content FROM framework WHERE name = :name_value", nativeQuery = true)
    String findcontent(@Param("name_value")String nameValue);

    @Query(value = "SELECT name FROM framework", nativeQuery = true)
    List<String> findAllName();

    FrameworkEntity findByName(String name);
}
