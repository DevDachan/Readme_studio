package com.readme.rss.data.service.Impl;

import com.readme.rss.data.dto.ProjectDTO;
import com.readme.rss.data.entity.ProjectEntity;
import com.readme.rss.data.handler.ProjectHandler;
import com.readme.rss.data.service.ProjectService;
import java.io.IOException;
import java.util.List;
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

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public ProjectDTO saveProject(String id, String file_name, String file_path, String file_content, String detail){
        ProjectEntity projectEntity = projectHandler.saveProjectEntity(id, file_name, file_path, file_content, detail);

        ProjectDTO projectDTO = new ProjectDTO(projectEntity.getId(), projectEntity.getFile_name(), projectEntity.getFile_path(),projectEntity.getFile_content(), projectEntity.getDetail());
        return projectDTO;
    }

    @Override
    public ProjectDTO getProject(String id){
        ProjectEntity projectEntity = projectHandler.getProjectEntity(id);

        ProjectDTO projectDTO = new ProjectDTO(projectEntity.getId(), projectEntity.getFile_name(), projectEntity.getFile_path(), projectEntity.getFile_content(), projectEntity.getDetail());
        return projectDTO;
    }

    @Override
    public List<ProjectEntity>  getController(String projectId){
        List<ProjectEntity> result = projectHandler.getController(projectId);
        return result;
    }

    @Override
    public List<String> getIdAll(){
        return projectHandler.getIdAll();
    }

    @Override
    public List<ProjectEntity> getFileContent(String id){
        return projectHandler.getFileContent(id);
    }

    @Override
    public String getFileContentByFileName(String id, String file_name){
        return projectHandler.getFileContentByFileName(id, file_name);
    }


    @Override
    public String getHeader(String framework, String repo_name){
        return framework.replace("repoName",repo_name);
    }

    @Override
    public String getContributor(String framework, String repo_name, String user_name){
        return framework.replace("repositoryName", repo_name).replace("userName", user_name);
    }

    @Override
    public String getSocial(String social_temp, String user_name) throws IOException {
        String content = "";
        String url = "https://github.com/";
        url = url + user_name;
        String[] social_link = {"instagram", "facebook", "linkedin", "notion", "twitter", "github", "gmail"};
        String[] logo_color = {"E4405F","1877F2","0A66C2","000000","1DA1F2","181717","F06B66" };

        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.getElementsByClass("vcard-details");
        for (Element headline : elements) {
            String[] urlparsing = headline.text().split(" ");
            for (int i = 0; i < urlparsing.length; i++) {
                for( int j = 0; j< social_link.length; j++){
                    if(urlparsing[i].contains(social_link[j])){
                        String temp= social_link[j]+"_Link";
                        String temp_data=" ";
                        temp_data=social_temp.replace("logo_color",logo_color[j]);
                        temp_data=temp_data.replace("social",social_link[j]);
                        temp_data=temp_data.replace(temp, urlparsing[i]);
                        content +=temp_data;
                    }
                }
            }
        }
        return content;
    }

    @Override
    public String getWebAPI(String projectId){
        List<ProjectEntity> result = projectHandler.getController(projectId);
        String mdResult = "<!-- Web API -->\n"
            + "|HTTP|API|URL|Return Type|Parameters|\n"
            + "|----|----|---|---|---|\n";

        int start_index = 0, end_index = 0;
        String urlTemp, returnType, parameters;
        String[] apiTemp;
        String current_content;

        for(int i = 0; i < result.size(); i++){
            current_content = result.get(i).getFile_content();
            mdResult += "|**"+  result.get(i).getFile_name()+"**|\n";

            // find post mapping
            while(true){
                // indexOf(String str, int fromIndex)
                start_index = current_content.indexOf("@PostMapping(", end_index);
                end_index = current_content.indexOf(")", start_index);

                if(start_index < 0){
                    break;
                } else{
                    urlTemp = current_content.substring(start_index,end_index);
                    urlTemp = urlTemp.split("\"")[1];

                    start_index = current_content.indexOf("public", end_index);
                    end_index = current_content.indexOf("(", start_index);
                    apiTemp = current_content.substring(start_index,end_index).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    start_index = current_content.indexOf("(", end_index);
                    end_index = current_content.indexOf("{", start_index);
                    parameters = current_content.substring(start_index+1,end_index);
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
                start_index = current_content.indexOf("@GetMapping(", end_index);
                end_index = current_content.indexOf(")", start_index);

                if(start_index < 0){
                    break;
                } else{
                    urlTemp = current_content.substring(start_index,end_index);
                    urlTemp = urlTemp.split("\"")[1];

                    start_index = current_content.indexOf("public", end_index);
                    end_index = current_content.indexOf("(", start_index);
                    apiTemp = current_content.substring(start_index,end_index).split(" ");
                    returnType = "";
                    for(int k = 1; k < apiTemp.length-1; k++){
                        returnType += apiTemp[k];
                    }

                    start_index = current_content.indexOf("(", end_index);
                    end_index = current_content.indexOf("{", start_index);
                    parameters = current_content.substring(start_index+1,end_index);
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


}