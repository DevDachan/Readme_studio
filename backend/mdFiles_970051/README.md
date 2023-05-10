<h1>README.md File</h1>

![header](https://capsule-render.vercel.app/api?type=Egg&&color=979494&fontColor=988686&height=300&section=header&text=NodeJS-TeamCCService&fontSize=70)

<!-- empty_textarea -->
🚪 Stack : Spring boot    
🌠 Version:  3.0.4   
📕 Gruop ID : com.readme   
📘 Artifact ID : rss   
📙 Java Version :17   
📚 DB : 

## Project Period <br><img src="https://ifh.cc/g/2jWwt7.png" width=100%><span style="width:20%"><span/><span style="margin-right: 60%; margin-left: 4%;" id="start_date">Start Date</span><span width=20% id="end_date">End Date</span>

## Project Architecture (Tree Structure)<br>
<!-- Project Architecture -->
```bash
.   
├── LICENSE   
├── README.md   
├── mvnw   
├── mvnw.cmd   
├── pom.xml   
└── src   
    ├── main   
    │   ├── java   
    │   │   └── com   
    │   │       └── readme   
    │   │           └── rss   
    │   │               ├── RssApplication.java   
    │   │               ├── controller   
    │   │               │   ├── MarkdownController.java   
    │   │               │   └── UnzipController.java   
    │   │               └── data   
    │   │                   ├── dao   
    │   │                   │   ├── FrameworkDAO.java   
    │   │                   │   ├── Impl   
    │   │                   │   │   ├── FrameworkDAOImpl.java   
    │   │                   │   │   ├── ProjectDAOImpl.java   
    │   │                   │   │   ├── TemplateDAOImpl.java   
    │   │                   │   │   └── UserDAOImpl.java   
    │   │                   │   ├── ProjectDAO.java   
    │   │                   │   ├── TemplateDAO.java   
    │   │                   │   └── UserDAO.java   
    │   │                   ├── dto   
    │   │                   │   ├── FrameworkDTO.java   
    │   │                   │   ├── ProjectDTO.java   
    │   │                   │   ├── TemplateDTO.java   
    │   │                   │   └── UserDTO.java   
    │   │                   ├── entity   
    │   │                   │   ├── FrameworkEntity.java   
    │   │                   │   ├── ProjectEntity.java   
    │   │                   │   ├── TemplateEntity.java   
    │   │                   │   ├── UserEntity.java   
    │   │                   │   └── compositeKey   
    │   │                   │       └── ProjectPK.java   
    │   │                   ├── handler   
    │   │                   │   ├── FrameworkHandler.java   
    │   │                   │   ├── Impl   
    │   │                   │   │   ├── FrameworkHandlerImpl.java   
    │   │                   │   │   ├── ProjectHandlerImpl.java   
    │   │                   │   │   ├── TemplateHandlerImpl.java   
    │   │                   │   │   └── UserHandlerImpl.java   
    │   │                   │   ├── ProjectHandler.java   
    │   │                   │   ├── TemplateHandler.java   
    │   │                   │   └── UserHandler.java   
    │   │                   ├── repository   
    │   │                   │   ├── FrameworkRepository.java   
    │   │                   │   ├── ProjectRepository.java   
    │   │                   │   ├── TemplateRepository.java   
    │   │                   │   └── UserRepository.java   
    │   │                   └── service   
    │   │                       ├── FrameworkService.java   
    │   │                       ├── Impl   
    │   │                       │   ├── FrameworkServiceImpl.java   
    │   │                       │   ├── ProjectServiceImpl.java   
    │   │                       │   ├── TemplateServiceImpl.java   
    │   │                       │   └── UserServiceImpl.java   
    │   │                       ├── ProjectService.java   
    │   │                       ├── TemplateService.java   
    │   │                       └── UserService.java   
    │   └── resources   
    │       └── application.properties   
    └── test   
        └── java   
            └── com   
                └── readme   
                    └── rss   
                        └── RssApplicationTests.java   
   
25 directories, 47 files   
```


## Web API<br><!-- Web API -->
 |HTTP|API|URL|Return Type|Parameters|
|---|---|---|---|---|
|**MarkdownController.java**|
|Post |makeMDzipFile()|/mdZipFile|byte[]|@RequestBody List<Map<String<br>Object>>readme|
|Post |makeMDfile()|/mdFile|byte[]|@RequestBody Map<String<br>Object>readme|
|**UnzipController.java**|
|Post |getFileData()|/register|HashMap<String,Object>|@RequestParam("jsonParam1") String jsonParam1<br>@RequestParam("jsonParam2") String jsonParam2<br>@RequestParam("file") MultipartFile file|
|Post |saveData()|/framework|String|@RequestParam("project_id") String project_id<br>@RequestParam("framework_name") String framework_name|
|Post |editPeriodImage()|/editPeriod|String|@RequestParam("start_date") String start_date<br>@RequestParam("end_date") String end_date|
|Post |alldata()|/alldata|String|@RequestParam("project_id") String project_id|


<!-- DB Table -->
 ### # 🌱 Framework Table  <br> 
|*Column Name*|
|-----|
|name **(PK)**|
|type|
|content|

### # 🌱 Project Table  <br> 
|*Column Name*|
|-----|
|id **(PK)**|
|file_name **(PK)**|
|file_path **(PK)**|
|file_content|
|detail|

### # 🌱 Template Table  <br> 
|*Column Name*|
|-----|
|templateId **(PK)**|
|templateContributor|

### # 🌱 User Table  <br> 
|*Column Name*|
|-----|
|project_id **(PK)**|
|user_name|
|repository_name|



## Dependencies<br>
<a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/1-spring--boot--starter--web-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/2-spring--boot--starter--data--jpa-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/3-mariadb--java--client-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/4-spring--boot--starter--validation-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/5-spring--boot--configuration--processor-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/6-lombok-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/7-spring--boot--starter--test-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/8-commons--io (version: 2.1)-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/9-spring--test-9cf"></a>   
```bash
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mariadb.jdbc</groupId>
        <artifactId>mariadb-java-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.1</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
    </dependency>
</dependencies>
```

## License
The MIT License (MIT)

Copyright (c) 2023 devDachan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Social<br><a href="https://www.instagram.com/da_chan_seo" target="_blank"><img src="https://img.shields.io/badge/instagram-E4405F?style=flat-square&logo=instagram&logoColor=white"/></a><a href="https://dachan.notion.site/4c179084d8c5490195db16b304eb050f" target="_blank"><img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white"/></a>

## Contributor<br><a href="https://github.com/devDachan/NodeJS-TeamCCService/graphs/contributors" target="_blank"> <img src="https://contrib.rocks/image?repo=devDachan/NodeJS-TeamCCService" /> </a>


