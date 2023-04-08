package com.readme.rss.data.service;

import com.readme.rss.data.dto.FrameworkDTO;
import java.util.HashMap;
import java.util.Map;

public interface FrameworkService {
    FrameworkDTO saveFramework(String type, String name, String content);

    FrameworkDTO getFramework(String name);
}

