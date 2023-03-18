package com.readme.rss.data.entity.compositeKey;

public class ProjectPK {
    String id;
    String file_name;
    String file_path;

    public ProjectPK(String id, String file_name, String file_path) {
        this.id = id;
        this.file_name = file_name;
        this.file_path = file_path;
    }
}
