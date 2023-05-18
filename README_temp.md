<h1>README.md File</h1>

![header](https://capsule-render.vercel.app/api?type=Waving&&color=979494&fontColor=black&height=300&section=header&text=README_STUDIO&fontSize=70)



## Project Period <br><img src="https://ifh.cc/g/LGBnpy.png" width=100%><span style="width:20%"><span/><span style="margin-right: 60%; margin-left: 4%;" id="start_date">2023-03-07</span><span width=20% id="end_date">2023-05-12</span>

<!-- empty_textarea -->

# 📔목차

- 프로젝트 설명
    - 주제
    - 기획 배경
    - 기술 스택
- Getting Started
    - 설치
- 사용 방법
- Contributors
- Trouble Shooting

<!-- empty_textarea -->
# 🌟 프로젝트 설명
![image](https://user-images.githubusercontent.com/70511859/230717670-6908fdca-fda5-4c54-b004-2a9d1a0e7449.png)

<!-- empty_textarea -->

## 주제

- 깃허브 레포지토리 링크나 프로젝트 파일을 올리면 해당 내용으로 README를 자동생성 해주고, 부가적으로 사용자가 원하는 옵션으로 반영이 되도록 해주는 Readme.md 파일 자동생성 웹 서비스

<!-- empty_textarea -->
## 기획 배경

- 프로젝트를 진행하며 Github에 업로드 이후 README를 작성해야 하는데 생각보다 많은 시간이 들어가고 번거로운 부분이 많다. 특히 프로젝트에 대한 내용을 작성할 때 하나하나 프로젝트 내에서 찾아 적는 것이 귀찮았다.

<!-- empty_textarea -->
## 서비스 대상

- Github README 파일로 포트폴리오를 만들고 싶어하는 대학생
- 프로젝트에 대해 적극적으로 홍보하고 싶어하는 사람들
- Spring Boot (maven) 기반 프로젝트의 README 파일을 작성하고자 하는 사람들

<!-- empty_textarea -->

## 서비스 목적

- 직접 README 파일의 모든 내용을 작성 할 필요 없이 프로젝트의 소스코드를 파싱해서 README의 내용들을 작성할 수 있는 인터페이스를 제공한다.
- Spring Boot의 구조를 파악하고 해당 구조에 따른 Database의 구조와 사용 방식등을 포함하도록 한다.
- README 파일에서 가시성을 높여주는 여러 시각적 요소(배지, 그래프, 테이블 등)등을 링크를 입력할 필요 없이 자동으로 만들어 줌으로써 README파일을 작성 할 때 귀찮음을 덜어준다.

  

<!-- empty_textarea -->
### 🚀 기술 스택

<ul>
<li><a href="[https://spring.io/](https://nextjs.org/)">Spring Boot</a></li>
<li><a href="[https://reactjs.org/](https://reactjs.org/)">React.js</a></li>
<li><a href="[https://mariadb.org/](https://nestjs.com/)">MariaDB</a></li>

<li><a href="[https://www.jetbrains.com/idea/](https://circleci.com/)">IntelliJ</a></li>
</ul>

<!-- empty_textarea -->

# **🧰** Getting Started

## ⚠️ 호환

- SpringBoot Controller 코드 내에서, Mac OS와 Window의 ProcessBuilder 코드가 다릅니다.
- OS 환경에 맞게 코드를 변경 후, 사용해주세요. (mac, window로 주석처리 되어 있습니다)

```java
// EX 

builder.command("mkdir", "unzipFiles"); // mac
builder.command("cmd.exe","/c","mkdir", "unzipFiles"); // window
```

## **⚙️** 설치

- 해당 프로젝트를 Local 환경에 clone 받아야 합니다.

```cpp
git clone https://github.com/DevDachan/Readme_generate
```

- React에서 해당 프로젝트에 필요한 package를 설치해야 합니다.

```
$ npm install
```

## 👀 Usage

1. spring boot project의 Github Repository URL을 입력한다.
2. 이후 화면 우측에 제공되는 interface를 통해서 README에 대한 내용을 작성하도록 한다.

 3. 최종적으로 작성된 README 파일을 다운로드한다. 

<!-- empty_textarea -->
# ⚠️ **Troubleshooting**

1. Markdown 언어를 렌더링시켜주는 패키지를 사용했을때 markdown 테이블을 그대로 수정 할 수 없고 단순 텍스트를 수정하도록 기능을 제공한다
-> 이러한 부분을 해결하고자 리엑트 측에서 Markdown <-> HTML 을 변환시켜주는 함수를 직접 구현해 사용하도록 했다.
-> 테이블 내용을 수정할 때에는 <td> 태그에 contentEditable 옵션을 통해 직접 내용 수정이 가능하도록 함.

• contentEditable을 사용시 <,>,\n,' '등의 문자들이 &lt,&gt등 HTML 특수 문자로 치환되어 값을 반환했다.
이러한 데이터들은 모두 기본 문자들로 치환해주는 과정이 필요함.
• td태그에 contentEditable을 줄 경우에는 innerHTML 값이 String data 형식으로 저장이 되므로 사용자가 태그값을 입력하지 못하게 막기가 가능하다.

=> 이러한 방법을 통해 테이블을 나타낼 경우 사용자에게 더욱 쉬운 접근 방식으로 markdonw 테이블을 수정 할 수있는 기능을 제공이 가능하다.


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


## Web API<br><!-- Web API -->
|HTTP|API|URL|Return Type|Parameters|
|----|----|---|---|---|
|**MarkdownController.java**|
| Post |makeMDzipFile()|/mdZipFile|byte[]|@RequestBody List<Map<String<br> Object>> readme|
|**RegisterServiceImpl.java**|
|**UnzipController.java**|
| Post |getFileData()|/register2|HashMap<String,Object>|@RequestParam("jsonParam1") String repoLink|
| Post |saveData()|/framework|String|@RequestParam("projectId") String projectId<br>         @RequestParam("frameworkName") String frameworkName|
| Post |editPeriodImage()|/editPeriod|String|         @RequestParam("start_date") String startDate<br>         @RequestParam("end_date") String endDate|
| Post |allData()|/alldata|Map<String,String[]>|@RequestParam("projectId") String projectId|


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
```
The MIT License (MIT)

Copyright (c) 2023 DevDachan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

 ## Social<br><a href="https://www.instagram.com/da_chan_seo" target="_blank"><img src="https://img.shields.io/badge/instagram-E4405F?style=flat-square&logo=instagram&logoColor=white"/></a><br><a href="https://dachan.notion.site/4c179084d8c5490195db16b304eb050f" target="_blank"><img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white"/></a><br>



# **👋 Contributors**

[🧑🏻‍💻 MinSoo Kim](https://github.com/geodo2)

[🧑🏻‍💻 DaChan Seo](https://github.com/DevDachan)

[👩🏻‍💻 YeJi Hong](https://github.com/YeJi222)
