<h1>testFile1.md File</h1>

![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=300&section=header&text=repoName&fontSize=90)


**Contributor**<br /><a href="https://github.com/devDachan/NodeJS-TeamCCService/graphs/contributors"> <img src="https://contrib.rocks/image?repo=devDachan/NodeJS-TeamCCService" /> </a>
<div style="font-weight:bold; font-size: 21px;">Project Period</div><div><img src='https://ifh.cc/g/LGBnpy.png' width=100%></div><span style="width:20%"><span/><span style="margin-right: 55%; margin-left: 5%;">Start Date</span><span width=20%>End Date</span></br>   
---
## Table of contents[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#table-of-contents)   </br>
- [Install](#install)
- [DB](#db)
- [queryMethod](#querymethod)
---

## Install[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#install)
<div align="right">[ <a href="#table-of-contents">↑ to top ↑</a> ]</div>
1. Java 설치
   - Spring Boot를 사용하려면 Java 8 버전 이상이 필요합니다.<br><br />
   - [Oracle Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) .<br><br />


```
sdk install spring boot
```
2. Spring Boot CLI 설치

   Spring Boot CLI는 Spring Boot 애플리케이션을 빠르게 만들 수 있는 명령줄 도구입니다.
```
 spring init --dependencies=web myproject
```
## DB[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#db)
<div align="right">[ <a href="#table-of-contents">↑ to top ↑</a> ]</div>


## QueryMethod[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#querymethod)
<div align="right">[ <a href="#table-of-contents">↑ to top ↑</a> ]</div>

- 데이터베이스에서 name이 "John Doe"이거나 age가 18 이상인 Person 엔티티를 조회하는 쿼리 메소드
```
 public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameOrAgeGreaterThanEqual(String name, int age);
}
```
- 데이터베이스에서 age가 18 이상인 Person 엔티티를 age를 기준으로 오름차순으로 조회하는 쿼리 메소드
```
 public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByAgeGreaterThanEqualOrderByAgeAsc(int age);
}
```



