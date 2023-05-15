package com.readme.rss.controller;

import com.readme.rss.data.dto.UserDTO;
import com.readme.rss.data.service.RegisterService;
import java.util.LinkedHashMap;
import java.util.Map;
import com.readme.rss.data.service.FrameworkService;
import com.readme.rss.data.service.ProjectService;
import com.readme.rss.data.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3005")
@RestController
public class UnzipController {
    private ProjectService projectService;
    private UserService userService;
    private FrameworkService frameworkService;
    private RegisterService registerService;

    @Autowired
    public UnzipController(ProjectService projectService, UserService userService, FrameworkService frameworkService,
        RegisterService registerService) {
        this.projectService = projectService;
        this.userService = userService;
        this.frameworkService = frameworkService;
        this.registerService = registerService;
    }

    @PostMapping(value = "/register")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String jsonParam1,
        @RequestParam("jsonParam2") String jsonParam2, @RequestParam("file") MultipartFile file)
        throws IOException, InterruptedException {
        UserDTO userInfo = userService.registerUser(jsonParam1,jsonParam2);

        String userName = userInfo.getUserName();
        String repositoryName = userInfo.getRepositoryName();
        String id = userInfo.getProjectId();

        HashMap<String, Object> zipContent = registerService.register(userName,repositoryName , file ,id);

        // project architecture project table에 insert
        projectService.saveProject(id, "Project Architecture", "", (String) zipContent.get("Architecture"), "tree");

        projectService.saveData(id, (List<String>) zipContent.get("javaFileName"), (List<String>) zipContent.get("javaFilePath"),
            (List<String>) zipContent.get("javaFileContent"),(List<String>) zipContent.get("javaFileDetail"));


        HashMap<String,String> projectScript = projectService.getProjectDetail(id);
        HashMap<String,Object> proectDetail = registerService.parseData(projectScript.get("noWhiteSpaceXml"), projectScript.get("propertiesContent"));
        List<String> frameworkNameList = frameworkService.getFrameworkNameList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("frameworkList",frameworkNameList);
        map.put("readmeName", "Readme.md"); // Readme.md
        map.put("springBootVersion", proectDetail.get("springBootVersion")); // springboot 버전
        map.put("groupId", proectDetail.get("groupId")); // groupId
        map.put("artifactId", proectDetail.get("artifactId")); // artifactId
        map.put("javaVersion", proectDetail.get("javaVersion")); // javaVersion
        map.put("databaseName", proectDetail.get("databaseName")); // db명
        map.put("projectId", id); // index(project_id)

        return map;
    }

    @PostMapping(value = "/register2")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String repoLink)
        throws IOException, InterruptedException {
        UserDTO userInfo = userService.registerUserLink(repoLink);
        String id = userInfo.getProjectId();

        HashMap<String, Object> clone = registerService.registerLink(repoLink,id);

        // project architecture project table에 insert
        projectService.saveProject(id, "Project Architecture", "", (String) clone.get("Architecture"), "tree");

        projectService.saveData(id, (List<String>) clone.get("javaFileName"), (List<String>) clone.get("javaFilePath"),
            (List<String>) clone.get("javaFileContent"),(List<String>) clone.get("javaFileDetail"));


        HashMap<String,String> projectScript = projectService.getProjectDetail(id);
        HashMap<String,Object> proectDetail = registerService.parseData(projectScript.get("noWhiteSpaceXml"), projectScript.get("propertiesContent"));
        List<String> frameworkNameList = frameworkService.getFrameworkNameList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("frameworkList",frameworkNameList);
        map.put("readmeName", "Readme.md"); // Readme.md
        map.put("springBootVersion", proectDetail.get("springBootVersion")); // springboot 버전
        map.put("groupId", proectDetail.get("groupId")); // groupId
        map.put("artifactId", proectDetail.get("artifactId")); // artifactId
        map.put("javaVersion", proectDetail.get("javaVersion")); // javaVersion
        map.put("databaseName", proectDetail.get("databaseName")); // db명
        map.put("projectId", id); // index(project_id)

        return map;
    }

    @PostMapping("/framework")
    public String saveData(@RequestParam("projectId") String projectId,
        @RequestParam("frameworkName") String frameworkName) throws IOException {
        String frameContent = "";
        UserDTO userDTO = userService.getUser(projectId);
        String userName = userDTO.getUserName();
        String repoName = userDTO.getRepositoryName();

        // framework_id에 따른 content제공
        if(frameworkName.equals("Contributor")){
            String framework = frameworkService.findContent(frameworkName);
            frameContent = projectService.getContributor(framework,repoName,userName);

        } else if (frameworkName.equals("Header")) { /* header 값에 대한 framework*/
            String framework = frameworkService.findContent("Header");
            frameContent = projectService.getHeader(framework, repoName);

        } else if (frameworkName.equals("Period")) {
            String framework = frameworkService.findContent("Period");
            frameContent = projectService.getPeriod(framework);

        } else if(frameworkName.equals("WebAPI")) {
            frameContent = frameworkService.findContent("WebAPI");
            frameContent += projectService.getWebAPI(projectId);
        } else if (frameworkName.equals("Social")){
            frameContent = "## Social<br>";
            String framework = frameworkService.findContent("Social");
            frameContent += projectService.getSocial(framework, userName);

        }else if (frameworkName.equals("Dependency")) {
            String framework = frameworkService.findContent("Dependency");
            frameContent = projectService.getDependency(projectId,framework);

        } else if (frameworkName.equals("DB Table")) {
            frameContent = frameworkService.findContent("DB Table");
            frameContent += projectService.getDBTable(projectId);

        } else if (frameworkName.equals("License")) {
            frameContent = projectService.getLicense(projectId, userName);

        } else if (frameworkName.equals("Architecture")) {
            frameContent =  frameworkService.findContent("Architecture");
            frameContent += projectService.getArchitecture(projectId, "Project Architecture");
        }

        return frameContent;
    }

    @PostMapping("/editPeriod")
    public String editPeriodImage(
        @RequestParam("start_date") String startDate,
        @RequestParam("end_date") String endDate) {

        return frameworkService.changePeriod(startDate,endDate);
    }

    @PostMapping("/alldata")
    public Map <String,String[]> allData(@RequestParam("projectId") String projectId) throws IOException {
        Map<String, String[]> allData = new LinkedHashMap<>();
        String frameContent = "";
        UserDTO userDTO = userService.getUser(projectId);
        String userName = userDTO.getUserName();
        String repoName = userDTO.getRepositoryName();
        String frameworkName="";
        List<String> frameworkNameList = frameworkService.getFrameworkNameList();
        int index=0;
        // framework 테이블에 있는 framework 다 가져오기
        //배열선언
        String[] frameworkList= new String[frameworkNameList.size()];
        String[] contentList= new String[frameworkNameList.size()];

        for(int count=0; count< frameworkNameList.size(); count++){
            frameworkName=frameworkNameList.get(count);

            // framework_id에 따른 content제공
            if(frameworkName.equals("Contributor")){
                String framework = frameworkService.findContent(frameworkName);
                frameContent = projectService.getContributor(framework,repoName,userName);
                index = 8;
            } else if (frameworkName.equals("Header")) { /* header 값에 대한 framework*/
                String framework = frameworkService.findContent("Header");
                frameContent = projectService.getHeader(framework, repoName);

                index = 0;
            } else if (frameworkName.equals("Period")) {
                String framework = frameworkService.findContent("Period");
                frameContent = projectService.getPeriod(framework);
                index = 1;
            } else if(frameworkName.equals("WebAPI")) {
                frameContent = frameworkService.findContent("WebAPI");
                frameContent += projectService.getWebAPI(projectId);
                index = 3;
            } else if (frameworkName.equals("Social")){
                frameContent = "## Social<br>";
                String framework = frameworkService.findContent("Social");
                frameContent += projectService.getSocial(framework, userName);

                index = 7;
            } else if (frameworkName.equals("Dependency")) {
                String framework = frameworkService.findContent("Dependency");
                frameContent = projectService.getDependency(projectId,framework);

                index = 5;
            } else if (frameworkName.equals("DB Table")) {
                frameContent = frameworkService.findContent("DB Table");
                frameContent += projectService.getDBTable(projectId);
                index = 4;
            } else if (frameworkName.equals("License")) {
                frameContent = projectService.getLicense(projectId, userName);

                index = 6;
            } else if (frameworkName.equals("Architecture")) {
                frameContent =  frameworkService.findContent("Architecture");
                frameContent += projectService.getArchitecture(projectId, "Project Architecture");
                index = 2;
            }
            frameworkList[index]=frameworkName;
            contentList[index]=frameContent;
        }
        allData.put("content",contentList);
        allData.put("type",frameworkList);

        return allData;
    }
}