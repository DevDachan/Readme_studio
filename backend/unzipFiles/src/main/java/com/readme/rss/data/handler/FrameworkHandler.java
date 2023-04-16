package com.readme.rss.data.handler;

import com.readme.rss.data.entity.FrameworkEntity;

public interface FrameworkHandler {
    FrameworkEntity saveFrameworkEntity(String type, String name, String content);

    FrameworkEntity getFrameworkEntity(String name);

}
