package com.readme.rss.data.dao;

import com.readme.rss.data.entity.FrameworkEntity;

public interface FrameworkDAO {
    FrameworkEntity saveFramework(FrameworkEntity frameworkEntity);
    FrameworkEntity getFramework (String name);
}
