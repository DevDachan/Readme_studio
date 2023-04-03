package com.readme.rss.data.service;

import com.readme.rss.data.dto.FrameworkDTO;

public interface FrameworkService {
    FrameworkDTO saveFramework(String id, String name, String content);

    FrameworkDTO getFramework(String id);
}
