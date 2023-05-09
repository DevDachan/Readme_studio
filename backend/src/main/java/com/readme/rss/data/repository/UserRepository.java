package com.readme.rss.data.repository;

import com.readme.rss.data.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {
  @Query(value = "SELECT DISTINCT project_id FROM user", nativeQuery = true)
  List<String> getIdAll();

}
