package com.readme.rss.data.dto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInfoDTO {
    String randomId;
    List<String> fileNameList;
    List<String> filePathList;
    List<String> fileContentList;
}