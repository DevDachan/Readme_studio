package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.handler.ProjectHandler;
import com.readme.rss.data.service.ProjectService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    ProjectHandler projectHandler;

    @Autowired
    public ProjectServiceImpl(ProjectHandler projectHandler){
        this.projectHandler = projectHandler;
    }

    @Override
    public void saveProject(String id, String fileName, String filePath, String fileContent, String detail){
        projectHandler.saveProject(id, fileName, filePath, fileContent, detail);
    }

    @Override
    public void saveData(String id, List<String> javaFileName,
        List<String> javaFilePath, List<String> javaFileContent,
        List<String> javaFileDetail
        ){
      // project table에 .java 파일만 insert
      for(int i = 0 ; i < javaFileName.size() ; i++){
        try{
          this.saveProject(id, javaFileName.get(i), javaFilePath.get(i), javaFileContent.get(i), javaFileDetail.get(i));
        } catch (Exception e){
          System.out.print(javaFileName.get(i)); // 어느 파일이 길이가 긴지 확인
        }
      }
    }

    @Override
    public HashMap<String, String> getProjectDetail(String id){
      HashMap<String, String> map = new HashMap<>();
      // =============== pom.xml에서 필요한 데이터 파싱 =============== //
      String xmlContent = "";
      // =============== application.properties에서 필요한 데이터 파싱 =============== //
      String propertiesContent = "";

      List<ProjectDTO> getProjectTableRow = this.getFileContent(id);
      for(int i = 0 ; i < getProjectTableRow.size() ; i++){
        if(getProjectTableRow.get(i).getFilePath().contains("pom.xml")){
          xmlContent = getProjectTableRow.get(i).getFileContent();
        } else if(getProjectTableRow.get(i).getFilePath().contains("application.properties")){
          propertiesContent = getProjectTableRow.get(i).getFileContent();
        }
      }
      // 공백 제거한 xmlContent - 정규식을 쓰기 위해 줄바꿈 제거
      String noWhiteSpaceXml = xmlContent.replaceAll("\n", "");
      map.put("noWhiteSpaceXml", noWhiteSpaceXml);
      map.put("propertiesContent", propertiesContent);
      return map;
    }


    @Override
    public ProjectDTO getProject(String id){
        return projectHandler.getProject(id);
    }

    @Override
    public List<ProjectDTO>  getController(String projectId){
        List<ProjectDTO> result = projectHandler.getController(projectId);
        return result;
    }

    @Override
    public List<ProjectDTO> getFileContent(String id){
        return projectHandler.getFileContent(id);
    }


    /*   API get   */
    @Override
    public String getArchitecture(String id, String fileName){
        return projectHandler.getFileContentByFileName(id, fileName);
    }

    @Override
    public String getLicense(String projectId, String userName){
        List<ProjectDTO> getProjectTableRow = projectHandler.getFileContent(projectId);
        String LicenseFile = "default";
        String content = "";
        for (int i = 0; i < getProjectTableRow.size(); i++) {
            if (getProjectTableRow.get(i).getFilePath().contains("LICENSE")) {
                String str = getProjectTableRow.get(i).getFileContent();
                String firstLine = str.substring(0, str.indexOf("\n"));
                firstLine = firstLine.replace("License", "");
                firstLine = firstLine.trim();
                content = "## License\n" +
                    "![License: MPL 2.0](https://img.shields.io/badge/License_name-brightgreen.svg)";
                content = content.replace("License_name", firstLine);
                LicenseFile = "exist";
            }
        }
        if (LicenseFile.equals("default")) {
            content = "## License\n" +
                "The MIT License (MIT)\n" +
                "\n" +
                "Copyright (c) 2023 "+ userName +"\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n"
                +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n"
                +
                "\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.";
        }
        return content;
    }
    @Override
    public String getDBTable(String projectId){
        String dbTable = "\n<!-- DB Table -->\n";

        // entity parsing 하기 위해 entity 파일 찾기
        List<String> entityDir = new ArrayList<>();
        List<String> entityDirContent = new ArrayList<>();
        List<ProjectDTO> getProjectTableRow = projectHandler.getFileContent(projectId);

        for(int i = 0 ; i < getProjectTableRow.size() ; i++){
            if(getProjectTableRow.get(i).getFilePath().contains("ENTITY".toLowerCase())){
                if(getProjectTableRow.get(i).getDetail().equals("noImpl")){
                    entityDir.add(getProjectTableRow.get(i).getFileName());
                    entityDirContent.add(getProjectTableRow.get(i).getFileContent());
                }
            }
        }
        int tableLen = entityDir.size();
        for(int i = 0 ; i < tableLen ; i++) {
            String frameworkContent = entityDirContent.get(i);
            // @Table이 없어서 에러 뜨는 경우 - BaseEntity.java의 경우
            // 에러뜨는 경우 pass하도록 예외 처리
            int tableIdx = frameworkContent.indexOf("@Table(");
            if(tableIdx == -1){
                continue;
            }
            String tableNameLine = frameworkContent.substring(frameworkContent.indexOf("@Table("),
                frameworkContent.indexOf(")") + 1);
            String tableName = tableNameLine.split("\"")[1];

            dbTable += "#### 🌱 " + tableName + " Table\n"
                + "|*Column Name*|\n"
                + "|----|\n";

            // 주석처리 라인 지우기
            int startIdx = 0, endIdx = 0;
            List<String> commentLineList = new ArrayList<>();
            String commentLine = "";
            while(true) {
                // indexOf(String str, int fromIndex)
                startIdx = frameworkContent.indexOf("//", endIdx);
                endIdx = frameworkContent.indexOf("\n", startIdx);

                if (startIdx < 0) { // 주석처리 없는 경우 스킵
                    break;
                } else { // 주석처리 있는 경우 그 라인 리스트에 담기
                    commentLine = frameworkContent.substring(startIdx, endIdx);
                    commentLineList.add(commentLine);
                }
            }

            for(int k = 0 ; k < commentLineList.size() ; k++){ // 주석 라인들 다 지우기
                frameworkContent = frameworkContent.replace(commentLineList.get(k), "");
            }

            // 공백 제거한 xmlContent - 정규식을 쓰기 위해 줄바꿈 제거
            String noWhiteSpaceContent = frameworkContent.replaceAll("\n", " ");

            // class { 이후 내용만 get
            Pattern pattern4 = Pattern.compile("(class )(.*?)(\\{)");
            Matcher matcher4 = pattern4.matcher(noWhiteSpaceContent);
            while (matcher4.find()) {
                int afterBraceIdx = noWhiteSpaceContent.indexOf(matcher4.group(3).trim());
                noWhiteSpaceContent = noWhiteSpaceContent.substring(afterBraceIdx); // afterBrace
            }

            // column name parsing
            String[] dataType = {"String", "int", "long", "boolean", "char", "byte", "short", "float", "double"};

            for(int j = 0 ; j < dataType.length ; j++){
                String type = dataType[j];
                String pkColumn = "";

                if (noWhiteSpaceContent.contains(type)) {
                    Pattern pattern = Pattern.compile("(@Id )(.*?)(;)"); // find PK
                    Matcher matcher = pattern.matcher(noWhiteSpaceContent);

                    while (matcher.find()) {
                        pkColumn = matcher.group(2).trim() + matcher.group(3).trim();

                        // pk인 컬럼 추가
                        Pattern pattern2 = Pattern.compile("(" + type + " )(.*?)(;)");
                        Matcher matcher2 = pattern2.matcher(pkColumn);
                        while (matcher2.find()) {
                            String columnName = matcher2.group(2).trim() + " **(PK)**";
                            dbTable += "|" + columnName + "|\n";

                            // pkColumn 제거
                            noWhiteSpaceContent = noWhiteSpaceContent.replaceAll("@Id", "");
                            noWhiteSpaceContent = noWhiteSpaceContent.replaceAll(matcher2.group(), "");
                        }
                    }

                    // pk 아닌 컬럼 테이블에 추가
                    Pattern pattern3 = Pattern.compile("(" + type + " )(.*?)(;)");
                    Matcher matcher3 = pattern3.matcher(noWhiteSpaceContent);
                    while (matcher3.find()) {
                        String columnName = matcher3.group(2).trim();
                        dbTable += "|" + columnName + "|\n";
                    }
                }
            }
        }
        return dbTable;
    }


    @Override
    public String getPeriod(String framework){
        return framework.replace("PeriodImage", "https://ifh.cc/g/2jWwt7.png")
            .replace("startDate", "Start Date")
            .replace("endDate", "End Date");
    }

    @Override
    public String getHeader(String framework, String repoName){
        return framework.replace("repoName",repoName);
    }

    @Override
    public String getContributor(String framework, String repoName, String userName){
        return framework.replace("repositoryName", repoName).replace("userName", userName);
    }

    @Override
    public String getSocial(String socialTemp, String userName) throws IOException {
        String content = "";
        String url = "https://github.com/";
        url = url + userName;
        String[] socialLink = {"instagram", "facebook", "linkedin", "notion", "twitter", "github", "gmail"};
        String[] logoColor = {"E4405F","1877F2","0A66C2","000000","1DA1F2","181717","F06B66" };

        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.getElementsByClass("vcard-details");
        for (Element headline : elements) {
            String[] urlparsing = headline.text().split(" ");
            for (int i = 0; i < urlparsing.length; i++) {
                for( int j = 0; j< socialLink.length; j++){
                    if(urlparsing[i].contains(socialLink[j])){
                        String temp= socialLink[j]+"_Link";
                        String tempData=" ";
                        tempData=socialTemp.replace("logo_color",logoColor[j])
                                            .replace("social",socialLink[j])
                                            .replace(temp, urlparsing[i]);
                        content +=tempData;
                    }
                }
            }
        }
        return content;
    }

    @Override
    public String getWebAPI(String projectId){
        List<ProjectDTO> result = projectHandler.getController(projectId);
        String mdResult = "<!-- Web API -->\n"
            + "|HTTP|API|URL|Return Type|Parameters|\n"
            + "|----|----|---|---|---|\n";

        int startIndex = 0, endIndex = 0;
        String urlTemp, returnType, parameters;
        String[] apiTemp;
        String currentContent;

        for(int i = 0; i < result.size(); i++){
            currentContent = result.get(i).getFileContent();
            mdResult += "|**"+  result.get(i).getFileName()+"**|\n";

            // find post mapping
            while(true){
                // indexOf(String str, int fromIndex)
                startIndex = currentContent.indexOf("@PostMapping(", endIndex);
                endIndex = currentContent.indexOf(")", startIndex);

                if(startIndex < 0){
                    break;
                } else{
                    urlTemp = currentContent.substring(startIndex,endIndex);
                    urlTemp = urlTemp.split("\"")[1];

                    startIndex = currentContent.indexOf("public", endIndex);
                    endIndex = currentContent.indexOf("(", startIndex);
                    apiTemp = currentContent.substring(startIndex,endIndex).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    startIndex = currentContent.indexOf("(", endIndex);
                    endIndex = currentContent.indexOf("{", startIndex);
                    parameters = currentContent.substring(startIndex+1,endIndex);
                    parameters = parameters.substring(0,parameters.lastIndexOf(")"));
                    parameters= parameters.replace("," ,"<br>");
                    parameters= parameters.replace("\n" ," ");

                    mdResult += "| Post |" +
                        apiTemp[apiTemp.length-1]+"()" + "|" +
                        urlTemp + "|"+
                        returnType +"|"+
                        parameters +"|\n";
                }
            }
            // find get mapping
            while(true){
                startIndex = currentContent.indexOf("@GetMapping(", endIndex);
                endIndex = currentContent.indexOf(")", startIndex);

                if(startIndex < 0){
                    break;
                } else{
                    urlTemp = currentContent.substring(startIndex,endIndex);
                    urlTemp = urlTemp.split("\"")[1];

                    startIndex = currentContent.indexOf("public", endIndex);
                    endIndex = currentContent.indexOf("(", startIndex);
                    apiTemp = currentContent.substring(startIndex,endIndex).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    startIndex = currentContent.indexOf("(", endIndex);
                    endIndex = currentContent.indexOf("{", startIndex);
                    parameters = currentContent.substring(startIndex+1,endIndex);
                    parameters = parameters.substring(0,parameters.lastIndexOf(")"));
                    parameters= parameters.replace("," ,"<br>");
                    parameters= parameters.replace("\n" ," ");

                    mdResult += "| Get |" +
                        apiTemp[apiTemp.length-1]+"()" + "|" +
                        urlTemp + "|"+
                        returnType +"|"+
                        parameters +"|\n";
                }
            }
        }
        return mdResult;
    }

    @Override
    public String getDependency(String projectId, String framework){
        String Dependency = "Dependency";
        String xmlContent = "";
        List<ProjectDTO> getProjectTableRow = projectHandler.getFileContent(projectId);
        for(int i = 0 ; i < getProjectTableRow.size() ; i++) {
            if (getProjectTableRow.get(i).getFilePath().contains("pom.xml")) {
                xmlContent = getProjectTableRow.get(i).getFileContent();
            }
        }

        String noWhiteSpaceXml = xmlContent.replaceAll("\n", "");
        String dependencyTags = "\n" + findDependencies(noWhiteSpaceXml).get(1).toString();
        List<String> dependencyNameList = (List<String>) findDependencies(noWhiteSpaceXml).get(0);
        String dependencyName = "\n";
        String dependencyBtn = "<a href=\"https://mvnrepository.com/\"><img src=\"https://img.shields.io/badge/NUM-DEPENDENCYNAME-9cf\"></a>";
        for(int i = 0 ; i < dependencyNameList.size() ; i++){
            String tempBtn = dependencyBtn;
            String dependencyFormat = dependencyNameList.get(i);
            dependencyFormat = dependencyFormat.replace("-", "--");
            tempBtn = tempBtn.replace("NUM", Integer.toString(i+1));
            tempBtn = tempBtn.replace("DEPENDENCYNAME",  dependencyFormat);

            dependencyName = dependencyName + tempBtn + "   ";
        }

        String result = framework.replace("DependencyNames", dependencyName)
                            .replace("DependencyContents", dependencyTags);
        return result;
    }


    public static List<Object> findDependencies(String xmlContent) {
        String dependencies = "";
        String tempStr = "";
        Pattern pattern = Pattern.compile("(<dependencies>)(.*?)(</dependencies>)");
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            tempStr = matcher.group();
            tempStr = tempStr.replaceAll("\\s+", ""); // 연속된 공백 제거
        }

        tempStr = tempStr.replaceAll("<dependency>", "\n    <dependency>\n        ");
        tempStr = tempStr.replaceAll("<artifactId>", "\n        <artifactId>");
        tempStr = tempStr.replaceAll("<optional>", "\n        <optional>");
        tempStr = tempStr.replaceAll("<scope>", "\n        <scope>");
        tempStr = tempStr.replaceAll("<version>", "\n        <version>");
        tempStr = tempStr.replaceAll("</dependency>", "\n    </dependency>");
        tempStr = tempStr.replaceAll("</dependencies>", "\n</dependencies>");

        dependencies = "```bash\n" + tempStr + "\n```";

        List<String> dependencyContents = new ArrayList<>();
        pattern = Pattern.compile("(?<=\\<dependency>)(.*?)(?=<\\/dependency>)");
        matcher = pattern.matcher(xmlContent);
        while (matcher.find()) {
            dependencyContents.add(matcher.group());
        }

        List<String> dependencyName = new ArrayList<>();
        String artifactId = "";
        for(int i = 0 ; i < dependencyContents.size() ; i++){
            Pattern pattern2 = Pattern.compile("(?<=\\<artifactId>)(.*?)(?=<\\/artifactId>)");
            Matcher matcher2 = pattern2.matcher(dependencyContents.get(i));
            if(dependencyContents.get(i).contains("<version>")){
                if(matcher2.find()){
                    String tempArtifactId = matcher2.group();
                    Pattern pattern3 = Pattern.compile("(?<=\\<version>)(.*?)(?=<\\/version>)");
                    Matcher matcher3 = pattern3.matcher(dependencyContents.get(i));
                    if(matcher3.find()){
                        String version = matcher3.group();
                        artifactId = tempArtifactId + " (version: " + version + ")";
                    }
                }
            } else{
                if(matcher2.find()){
                    artifactId = matcher2.group();
                }
            }
            dependencyName.add(artifactId);
        }
        List<Object> retDependency = new ArrayList<>();
        retDependency.add(dependencyName);
        retDependency.add(dependencies);

        return retDependency;
    }
}