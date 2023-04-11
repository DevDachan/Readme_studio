# Project Name : Readme Studio Service(RSS) 개인 역할

![serviceName](https://capsule-render.vercel.app/api?type=waving&color=auto&height=300&section=header&text=RSS-Local&fontSize=90)

## 1. Task1
1. 스프링부트에서 로컬에 zip 파일 업로드
2. 업로드 되어 있는 zip 파일을 압축 해제 시킨 다음, 파일의 내용을 읽어서 특정 내용을 http request 보내는 작업(테스트 용으로 크롬에서 Http request 보내기)

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
5. 리액트 페이지로 데이터 잘 보내졌는지 확인하기
