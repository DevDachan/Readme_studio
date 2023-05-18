<h1>README.md File</h1>

![header](https://capsule-render.vercel.app/api?type=Cylinder&&color=4f99e8&fontColor=ffffff&height=300&section=header&text=README_STUDIO&fontSize=70)



<!-- empty_textarea -->
🚪 Stack : Spring boot    
🌠 Version:  3.0.4   
📕 Gruop ID : com.readme   
📘 Artifact ID : rss   
📙 Java Version :17   
📚 DB : MariaDB

## Project Period <br><img src="https://ifh.cc/g/LGBnpy.png" width=100%><span style="width:20%"><span/><span style="margin-right: 60%; margin-left: 4%;" id="start_date">2023-03-04</span><span width=20% id="end_date">2023-05-12</span>

## Project Architecture (Tree Structure)<br>
<!-- Project Architecture -->
```bash
.   
├── java   
│   └── com   
│       └── readme   
│           └── rss   
│               ├── controller   
│               │   ├── MarkdownController.java   
│               │   └── UnzipController.java   
│               ├── data   
│               │   ├── dao   
│               │   │   ├── FrameworkDAO.java   
│               │   │   ├── Impl   
│               │   │   │   ├── FrameworkDAOImpl.java   
│               │   │   │   ├── ProjectDAOImpl.java   
│               │   │   │   └── UserDAOImpl.java   
│               │   │   ├── ProjectDAO.java   
│               │   │   └── UserDAO.java   
│               │   ├── dto   
│               │   │   ├── FrameworkDTO.java   
│               │   │   ├── ProjectDTO.java   
│               │   │   └── UserDTO.java   
│               │   ├── entity   
│               │   │   ├── compositeKey   
│               │   │   │   └── ProjectPK.java   
│               │   │   ├── FrameworkEntity.java   
│               │   │   ├── ProjectEntity.java   
│               │   │   └── UserEntity.java   
│               │   ├── handler   
│               │   │   ├── FrameworkHandler.java   
│               │   │   ├── Impl   
│               │   │   │   ├── FrameworkHandlerImpl.java   
│               │   │   │   ├── ProjectHandlerImpl.java   
│               │   │   │   └── UserHandlerImpl.java   
│               │   │   ├── ProjectHandler.java   
│               │   │   └── UserHandler.java   
│               │   ├── repository   
│               │   │   ├── FrameworkRepository.java   
│               │   │   ├── ProjectRepository.java   
│               │   │   └── UserRepository.java   
│               │   └── service   
│               │       ├── FrameworkService.java   
│               │       ├── Impl   
│               │       │   ├── FrameworkServiceImpl.java   
│               │       │   ├── MdDownloadServiceImpl.java   
│               │       │   ├── ProjectServiceImpl.java   
│               │       │   ├── RegisterServiceImpl.java   
│               │       │   └── UserServiceImpl.java   
│               │       ├── MdDownloadService.java   
│               │       ├── ProjectService.java   
│               │       ├── RegisterService.java   
│               │       └── UserService.java   
│               └── RssApplication.java   
└── resources   
    └── application.properties   
   
17 directories, 36 files   
```


## Web API<br><!-- Web API -->
|HTTP|API|URL|Return Type|Parameters|
|----|----|---|---|---|
|**MarkdownController.java**|
| Post |makeMDzipFile()|/mdZipFile|byte[]|@RequestBody List<Map<String<br> Object>> readme|
|**UnzipController.java**|
| Post |getFileData()|/register2|HashMap<String,Object>|@RequestParam("jsonParam1") String repoLink|
| Post |saveData()|/framework|String|@RequestParam("projectId") String projectId<br>         @RequestParam("frameworkName") String frameworkName|
| Post |editPeriodImage()|/editPeriod|String|         @RequestParam("start_date") String startDate<br>         @RequestParam("end_date") String endDate|
| Post |allData()|/alldata|Map<String,String[]>|@RequestParam("projectId") String projectId|


## DB Table<br>
<!-- DB Table -->
#### 🌱 Framework Table
|*Column Name*|
|----|
|name **(PK)**|
|type|
|content|
#### 🌱 Project Table
|*Column Name*|
|----|
|id **(PK)**|
|fileName **(PK)**|
|filePath **(PK)**|
|fileContent|
|detail|
#### 🌱 User Table
|*Column Name*|
|----|
|projectId **(PK)**|
|userName|
|repositoryName|


## Dependencies<br>
<a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/1-spring--boot--starter--web-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/2-spring--boot--starter--data--jpa-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/3-mariadb--java--client-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/4-spring--boot--starter--validation-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/5-spring--boot--configuration--processor-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/6-lombok-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/7-spring--boot--starter--test-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/8-commons--io (version: 2.1)-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/9-spring--test-9cf"></a>   <a href="https://mvnrepository.com/"><img src="https://img.shields.io/badge/10-jsoup (version: 1.14.2)-9cf"></a>   
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
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.14.2</version>
    </dependency>
</dependencies>
```

## License
The MIT License (MIT)

Copyright (c) 2023 DevDachan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Social<br><a href="https://www.instagram.com/da_chan_seo" target="_blank"><img src="https://img.shields.io/badge/instagram-E4405F?style=flat-square&logo=instagram&logoColor=white"/></a><br><a href="https://dachan.notion.site/4c179084d8c5490195db16b304eb050f" target="_blank"><img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white"/></a><br>

## Contributor<br><a href="https://github.com/DevDachan/Readme_studio/graphs/contributors" target="_blank"> <img src="https://contrib.rocks/image?repo=DevDachan/Readme_studio" /> </a>
