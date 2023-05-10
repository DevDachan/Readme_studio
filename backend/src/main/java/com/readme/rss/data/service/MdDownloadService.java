package com.readme.rss.data.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MdDownloadService {
    void makeMdDirectory(String projectId, String mdFilesName, List<Map<String, Object>> readme) throws IOException;

    void writeMdContents(String mdFilesName, List<Map<String, Object>> readme) throws IOException;

    byte[] zipMdFiles(String mdFilesName) throws IOException;

    void deleteMdDirectory(String mdFilesName) throws IOException;
}
