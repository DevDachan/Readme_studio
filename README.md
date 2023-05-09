
# Readme Studio   

<p>    
<a href="https://github.com/DevDachan/Readme_generate/graphs/contributors">   
  <img src="https://img.shields.io/github/contributors/DevDachan/Readme_generate" alt="contributors" />      
</a>    

<a href="">   
<img src="https://img.shields.io/github/last-commit/DevDachan/Readme_generate" alt="last update" />   
</a>   
<a href="https://github.com/DevDachan/Readme_generate/network/members">   
<img src="https://img.shields.io/github/forks/DevDachan/Readme_generate" alt="forks" />   
</a>   
<a href="https://github.com/DevDachan/Readme_generate/stargazers">   
<img src="https://img.shields.io/github/stars/DevDachan/Readme_generate" alt="stars" />   
</a>   
<a href="https://github.com/DevDachan/Readme_generate/issues/)">   
<img src="https://img.shields.io/github/issues/DevDachan/Readme_generate" alt="open issues" />   
</a>
</p>

<br />

# 🗓️ 프로젝트 기간

### 2023.03.07 ~ Now

<img width="100%" alt="image" src="https://user-images.githubusercontent.com/70511859/230717626-4b6f146a-e442-4ead-9677-e7c068d37978.png">

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

# 🌟 프로젝트 설명
![image](https://user-images.githubusercontent.com/70511859/230717670-6908fdca-fda5-4c54-b004-2a9d1a0e7449.png)

## 주제

- 깃허브 레포지토리 링크나 프로젝트 파일을 올리면 해당 내용으로 README를 자동생성 해주고, 부가적으로 사용자가 원하는 옵션으로 반영이 되도록 해주는 Readme.md 파일 자동생성 웹 서비스

## 기획 배경

- 프로젝트를 진행하며 Github에 업로드 이후 README를 작성해야 하는데 생각보다 많은 시간이 들어가고 번거로운 부분이 많다. 특히 프로젝트에 대한 내용을 작성할 때 하나하나 프로젝트 내에서 찾아 적는 것이 귀찮았다.

## 서비스 대상

- Github README 파일로 포트폴리오를 만들고 싶어하는 대학생
- 프로젝트에 대해 적극적으로 홍보하고 싶어하는 사람들
- Spring Boot (maven) 기반 프로젝트의 README 파일을 작성하고자 하는 사람들

## 서비스 목적

- 직접 README 파일의 모든 내용을 작성 할 필요 없이 프로젝트의 소스코드를 파싱해서 README의 내용들을 작성할 수 있는 인터페이스를 제공한다.
- Spring Boot의 구조를 파악하고 해당 구조에 따른 Database의 구조와 사용 방식등을 포함하도록 한다.
- README 파일에서 가시성을 높여주는 여러 시각적 요소(배지, 그래프, 테이블 등)등을 링크를 입력할 필요 없이 자동으로 만들어 줌으로써 README파일을 작성 할 때 귀찮음을 덜어준다.

## Database ERD
![image](https://user-images.githubusercontent.com/70511859/230717716-83a5fe7f-0812-4d85-9cb0-478408bf203f.png)

### **framework**

| name | content |
| --- | --- |
| **name** | 제공되는 interface framework의 이름 |
| **content** | 해당 framework에서 사용되는 내용 (태그 정보) |
| **type** | 각 framework를 나누기 위한 ID값 |

### project

| name | content |
| --- | --- |   
| **ID** | 프로젝트에 부여된 고유 random id |   
| **file_name** | 파일 이름 |
| **file_path** | 파일이 프로젝트 내에서 저장된 위치 |
| **file_content** | 파일의 내용 |
| **detail** | 해당 파일의 종류 |

### user

| name | content |
| --- | --- |
| **project_id** | 프로젝트에 부여된 고유 random id |
| **repository_name** | 프로젝트 Github Repository 이름 |
| **user_name** | 프로젝트 관리자 Github name |

### 🚀 기술 스택

<ul>
<li><a href="[https://spring.io/](https://nextjs.org/)">Spring Boot</a></li>
<li><a href="[https://reactjs.org/](https://reactjs.org/)">React.js</a></li>
<li><a href="[https://mariadb.org/](https://nestjs.com/)">MariaDB</a></li>

<li><a href="[https://www.jetbrains.com/idea/](https://circleci.com/)">IntelliJ</a></li>
</ul>

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

# ⚠️ **Troubleshooting**

1. Markdown 언어를 렌더링시켜주는 패키지를 사용했을때 markdown 테이블을 그대로 수정 할 수 없고 단순 텍스트를 수정하도록 기능을 제공한다
-> 이러한 부분을 해결하고자 리엑트 측에서 Markdown <-> HTML 을 변환시켜주는 함수를 직접 구현해 사용하도록 했다.
-> 테이블 내용을 수정할 때에는 <td> 태그에 contentEditable 옵션을 통해 직접 내용 수정이 가능하도록 함.

• contentEditable을 사용시 <,>,\n,' '등의 문자들이 &lt,&gt등 HTML 특수 문자로 치환되어 값을 반환했다.
이러한 데이터들은 모두 기본 문자들로 치환해주는 과정이 필요함.
• td태그에 contentEditable을 줄 경우에는 innerHTML 값이 String data 형식으로 저장이 되므로 사용자가 태그값을 입력하지 못하게 막기가 가능하다.

=> 이러한 방법을 통해 테이블을 나타낼 경우 사용자에게 더욱 쉬운 접근 방식으로 markdonw 테이블을 수정 할 수있는 기능을 제공이 가능하다.

2. 




# **👋 Contributors**

[🧑🏻‍💻 MinSoo Kim](https://github.com/geodo2)

[🧑🏻‍💻 DaChan Seo](https://github.com/DevDachan)

[👩🏻‍💻 YeJi Hong](https://github.com/YeJi222)
