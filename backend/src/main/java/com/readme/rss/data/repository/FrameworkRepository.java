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
