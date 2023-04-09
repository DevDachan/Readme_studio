package com.readme.rss.data.dao;

import com.readme.rss.data.entity.FrameworkEntity;
import java.util.List;
import java.util.Map;

public interface FrameworkDAO {
    FrameworkEntity saveFramework(FrameworkEntity frameworkEntity);
    FrameworkEntity getFramework (String name);
    List<String> getFrameworkNameList();

    String findContent(String name);
}
