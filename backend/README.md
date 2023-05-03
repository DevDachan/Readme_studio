# Project Name : Readme Studio Service(RSS) 개인 역할

![serviceName](https://capsule-render.vercel.app/api?type=waving&color=auto&height=300&section=header&text=RSS-Local&fontSize=90)

## 1. Task1
1. 스프링부트에서 로컬에 zip 파일 업로드
2. 업로드 되어 있는 zip 파일을 압축 해제 시킨 다음, 파일의 내용을 읽어서 특정 내용을 http request 보내는 작업(테스트 용으로 크롬에서 Http request 보내기)

<img width="700" alt="image" src="https://user-images.githubusercontent.com/70511859/225084700-882226f8-a5b3-4ed7-8fc0-c355faf6c628.png">

(Detail)
1. 로컬로 업로드되어 있는 zip압축파일을 푼다.
2. 압축풀기를 하기 전, unzipFiles라는 임시 폴더를 만들어 그 안에 압축해제된 파일들을 임시 저장한다.
3. 압축 푼 파일들 중에서 원하는 정보를 찾는다.(ex. git 주소 찾기)
4. url을 찾아 파싱(테스트 용으로 매우 간단한 파일 내용을 구성하였기에, 추후 더 디테일한 파싱 작업 필요)
5. url path로 넘길 때, 사용할 수 없는 특수문자가 있기 때문에 포맷 형식을 바꿔준다.
6. url 매개변수로 찾은 content 전송(using HTTP request)
7. data 전송 후, 압축 풀기한 폴더 삭제

## 2. Task2
1. 리액트로 개발된 웹 페이지에서 zip 파일을 업로드 시키면, 그 zip 파일을 로컬에 임시저장
2. 임시저장한 zip 파일을 압축 해제 시킨 다음, 파일의 특정 내용을 읽어서 react axios response로 다시 보내기

(Detail)
1. 리액트(localhsot:3005)에서 zip 파일을 업로드 시키면, 스프링부트(localhost:8090)에서 받고 unzipTest.zip 파일로 임시저장
2. zip파일이 입력되기 전까지 기다렸다가, 입력되면 압축풀기 시작
3. 중간 과정은 Task1의 과정과 동일(2 ~ 5)
4. 파싱한 content를 리액트 axios response로 다시 보내기
5. 리액트 페이지로 데이터 잘 보내졌는지 확인

## 3. Task3
1. DB 연결
2. 리액트에서 userName, repName, zip 파일 보낸 것을 spring boot에서 받아서 Task2에서 진행한 적절한 처리
3. userName, repName, zip 파일을 활용하여 markdown 문법 형식으로 string 샘플 data를 만들고 react response로 리턴
   <img width="700" alt="image" src="https://user-images.githubusercontent.com/70511859/225084066-d87cca81-7032-41ef-bbec-4b017b6ea5d9.png">

## 4. Task4
1. service에서 sql insert 작업
2. Editor 페이지(react)에서 String array 보내주면 spring에서 md 파일로 만들어서 response로 다시 보내주기
3. Result 페이지에서 md 파일별로 다운받는 기능, 전체 md 파일 다운 받을 수 있도록 zip 파일로 다운 받는 기능 구현

(Detail)
1. project table과 user table의 id는 랜덤한 값으로 설정
2. Database ERD 구조

![image](https://user-images.githubusercontent.com/70511859/225482486-98702a8d-9b9d-4b8e-adbf-980d92448e86.png)

## 5. Task5
1. Table of Contents, framework 2개(커밋 캘린더랑, 기간) 중 framework 기간 구현 역할

(Detail)
1. end date가 입력된 경우, 아닌 경우 period image 다르게 해서 호스팅 링크 react로 보내주기
2. (추가 수정 메모) 리드미 파일 리스트를 보내주지 말고 Readme.md 스트링으로 보내주기(파일에 따른 리드미가 아닌 사용자가 추가할 수 있도록)

## 6. Task6
1. 4/1 토요일 밤까지 리드미 파트 별 정리 (토요일 밤 짧게 미팅 - framework 15개 정리)
2. 업로드 파일 파싱
3. project 테이블에 detail 컬럼 추가
4. 'dependency' framework 추가

(Detail)
1. java 파일들 먼저 찾고 그 파일들 path 구하기
2. Path 정보 보고 디렉토리별로 구분하기
3. content를 보고 파싱 시작 - 구조 찾기
4. pom.xml, application.properties, src/main/java/경로에 있는 주요 자바 파일들 db에 저장
5. project 테이블에서 file_content 컬럼 type 변경해주기 : varchar(15000) -> LONGTEXT
6. project 테이블의 detail 컬럼  
   -> Impl 파일인지 일반 자바 파일인지 pom.xml이나 application.properties 파일인지 구분
7. 스프링부트 버전, 프로젝트의 패키지명에서 groupId랑 artifactId 구분해서 파싱, 자바 jdk 버전, 프로젝트와 연결된 database명 파싱 + map data 수정
8. pom.xml 파싱해서 dependency명과 버전 추출, <dependencies>태그 코드블럭 형태로 추출
9. db에 Dependency 행 추가
   ![image](https://user-images.githubusercontent.com/70511859/229765078-36da6236-1125-4f25-8fb4-c1416b35ff37.png)

[dependencies 형식]
```pom.xml
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

## 7. Task7
1. A받은 framework 구현

### DB Table<br><!-- DB Table -->
- #### Framework Table
|*Column Name*|
|----|
|name **(PK)**|
|content|
|type|
- #### Project Table
|*Column Name*|
|----|
|id **(PK)**|
|file_name **(PK)**|
|file_path **(PK)**|
|file_content|
|detail|
- #### Template Table
|*Column Name*|
|----|
|templateId **(PK)**|
|templateContributor|
- #### User Table
|*Column Name*|
|----|
|project_id **(PK)**|
|user_name|
|repository_name|

## 8. Task8
1. Git Repository Link 받아서 파일 다운로드 및 처리   
   https://github.com/YeJi222/RS_FileCloneTest 깃허브 링크로 테스트 중   
   (+) 다른 스프링부트 프로젝트 링크도 확인 완료

2. Project Architecture tree 형식으로 데이터 보내기

### Project Architecture (Tree Structure)<br>
<!-- Project Architecture -->
```bash
.   
└── backend   
    ├── LICENSE   
    ├── README.md   
    ├── mvnw   
    ├── mvnw.cmd   
    ├── pom.xml   
    ├── rss.iml   
    ├── src   
    │   ├── main   
    │   │   ├── java   
    │   │   │   └── com   
    │   │   │       └── readme   
    │   │   │           └── rss   
    │   │   │               ├── RssApplication.java   
    │   │   │               ├── controller   
    │   │   │               │   ├── MarkdownController.java   
    │   │   │               │   └── UnzipController.java   
    │   │   │               └── data   
    │   │   │                   ├── dao   
    │   │   │                   │   ├── FrameworkDAO.java   
    │   │   │                   │   ├── Impl   
    │   │   │                   │   │   ├── FrameworkDAOImpl.java   
    │   │   │                   │   │   ├── ProjectDAOImpl.java   
    │   │   │                   │   │   ├── TemplateDAOImpl.java   
    │   │   │                   │   │   └── UserDAOImpl.java   
    │   │   │                   │   ├── ProjectDAO.java   
    │   │   │                   │   ├── TemplateDAO.java   
    │   │   │                   │   └── UserDAO.java   
    │   │   │                   ├── dto   
    │   │   │                   │   ├── FrameworkDTO.java   
    │   │   │                   │   ├── ProjectDTO.java   
    │   │   │                   │   ├── TemplateDTO.java   
    │   │   │                   │   └── UserDTO.java   
    │   │   │                   ├── entity   
    │   │   │                   │   ├── FrameworkEntity.java   
    │   │   │                   │   ├── ProjectEntity.java   
    │   │   │                   │   ├── TemplateEntity.java   
    │   │   │                   │   ├── UserEntity.java   
    │   │   │                   │   └── compositeKey   
    │   │   │                   │       └── ProjectPK.java   
    │   │   │                   ├── handler   
    │   │   │                   │   ├── FrameworkHandler.java   
    │   │   │                   │   ├── Impl   
    │   │   │                   │   │   ├── FrameworkHandlerImpl.java   
    │   │   │                   │   │   ├── ProjectHandlerImpl.java   
    │   │   │                   │   │   ├── TemplateHandlerImpl.java   
    │   │   │                   │   │   └── UserHandlerImpl.java   
    │   │   │                   │   ├── ProjectHandler.java   
    │   │   │                   │   ├── TemplateHandler.java   
    │   │   │                   │   └── UserHandler.java   
    │   │   │                   ├── repository   
    │   │   │                   │   ├── FrameworkRepository.java   
    │   │   │                   │   ├── ProjectRepository.java   
    │   │   │                   │   ├── TemplateRepository.java   
    │   │   │                   │   └── UserRepository.java   
    │   │   │                   └── service   
    │   │   │                       ├── FrameworkService.java   
    │   │   │                       ├── Impl   
    │   │   │                       │   ├── FrameworkServiceImpl.java   
    │   │   │                       │   ├── ProjectServiceImpl.java   
    │   │   │                       │   ├── TemplateServiceImpl.java   
    │   │   │                       │   └── UserServiceImpl.java   
    │   │   │                       ├── ProjectService.java   
    │   │   │                       ├── TemplateService.java   
    │   │   │                       └── UserService.java   
    │   │   └── resources   
    │   │       └── application.properties   
    │   └── test   
    │       └── java   
    │           └── com   
    │               └── readme   
    │                   └── rss   
    │                       └── RssApplicationTests.java   
    └── target   
        ├── classes   
        │   ├── application.properties   
        │   └── com   
        │       └── readme   
        │           └── rss   
        │               ├── RssApplication.class   
        │               ├── controller   
        │               │   ├── MarkdownController.class   
        │               │   └── UnzipController.class   
        │               └── data   
        │                   ├── dao   
        │                   │   ├── FrameworkDAO.class   
        │                   │   ├── Impl   
        │                   │   │   ├── FrameworkDAOImpl.class   
        │                   │   │   ├── ProjectDAOImpl.class   
        │                   │   │   ├── TemplateDAOImpl.class   
        │                   │   │   └── UserDAOImpl.class   
        │                   │   ├── ProjectDAO.class   
        │                   │   ├── TemplateDAO.class   
        │                   │   └── UserDAO.class   
        │                   ├── dto   
        │                   │   ├── FrameworkDTO$FrameworkDTOBuilder.class   
        │                   │   ├── FrameworkDTO.class   
        │                   │   ├── ProjectDTO$ProjectDTOBuilder.class   
        │                   │   ├── ProjectDTO.class   
        │                   │   ├── TemplateDTO$TemplateDTOBuilder.class   
        │                   │   ├── TemplateDTO.class   
        │                   │   ├── UserDTO$UserDTOBuilder.class   
        │                   │   └── UserDTO.class   
        │                   ├── entity   
        │                   │   ├── FrameworkEntity$FrameworkEntityBuilder.class   
        │                   │   ├── FrameworkEntity.class   
        │                   │   ├── ProjectEntity$ProjectEntityBuilder.class   
        │                   │   ├── ProjectEntity.class   
        │                   │   ├── TemplateEntity$TemplateEntityBuilder.class   
        │                   │   ├── TemplateEntity.class   
        │                   │   ├── UserEntity$UserEntityBuilder.class   
        │                   │   ├── UserEntity.class   
        │                   │   └── compositeKey   
        │                   │       └── ProjectPK.class   
        │                   ├── handler   
        │                   │   ├── FrameworkHandler.class   
        │                   │   ├── Impl   
        │                   │   │   ├── FrameworkHandlerImpl.class   
        │                   │   │   ├── ProjectHandlerImpl.class   
        │                   │   │   ├── TemplateHandlerImpl.class   
        │                   │   │   └── UserHandlerImpl.class   
        │                   │   ├── ProjectHandler.class   
        │                   │   ├── TemplateHandler.class   
        │                   │   └── UserHandler.class   
        │                   ├── repository   
        │                   │   ├── FrameworkRepository.class   
        │                   │   ├── ProjectRepository.class   
        │                   │   ├── TemplateRepository.class   
        │                   │   └── UserRepository.class   
        │                   └── service   
        │                       ├── FrameworkService.class   
        │                       ├── Impl   
        │                       │   ├── FrameworkServiceImpl.class   
        │                       │   ├── ProjectServiceImpl.class   
        │                       │   ├── TemplateServiceImpl.class   
        │                       │   └── UserServiceImpl.class   
        │                       ├── ProjectService.class   
        │                       ├── TemplateService.class   
        │                       └── UserService.class   
        └── generated-sources   
            └── annotations   
   
45 directories, 97 files   
```

## 9. Task9
1. unzip 파일명에 project id 부여하기 => OK
2. 파일 다운로드 에러 찾기 => OK
3. period 날짜 간격 해결 방안 찾기


### Memo
builder.directory(new File("./mdFiles")); // mdFiles로 이동   
