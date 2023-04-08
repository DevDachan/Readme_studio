package com.readme.rss.data.dao;

import com.readme.rss.data.entity.FrameworkEntity;
import java.util.Map;

public interface FrameworkDAO {
    FrameworkEntity saveFramework(FrameworkEntity frameworkEntity);
    FrameworkEntity getFramework (String name);

}
