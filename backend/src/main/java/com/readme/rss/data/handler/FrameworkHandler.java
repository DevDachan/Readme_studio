package com.readme.rss.data.handler;

import com.readme.rss.data.entity.FrameworkEntity;
import java.util.HashMap;
import java.util.Map;

public interface FrameworkHandler {
    FrameworkEntity saveFrameworkEntity(String type, String name, String content);

    FrameworkEntity getFrameworkEntity(String name);


}
