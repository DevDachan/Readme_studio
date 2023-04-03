package com.readme.rss.data.handler;

import com.readme.rss.data.entity.FrameworkEntity;

public interface FrameworkHandler {
    FrameworkEntity saveFrameworkEntity(String id, String name, String content);

    FrameworkEntity getFrameworkEntity(String id);

}
