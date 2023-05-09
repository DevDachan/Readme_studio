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

@CrossOrigin(origins = "http://localhost:3000")
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
      return registerService.register(jsonParam1, jsonParam2, file);
    }

    @PostMapping(value = "/register2")
    public HashMap<String, Object> getFileData(@RequestParam("jsonParam1") String repoLink)
        throws IOException, InterruptedException {

      return registerService.registerLink(repoLink);
    }

    @PostMapping("/framework")
    public String saveData(@RequestParam("project_id") String projectId,
        @RequestParam("framework_name") String frameworkName) throws IOException {
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
    public Map <String,String[]> allData(@RequestParam("project_id") String projectId) throws IOException {
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