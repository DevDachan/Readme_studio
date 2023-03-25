import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileResultList from "./File/ReadmeFileResultList";
import ReadmeFileContent from "./File/ReadmeFileContent";
import Controller from "./Controller/Controller";

<<<<<<< HEAD
=======

>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

<<<<<<< HEAD
=======


>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
function Result(props) {
  const navigate = useNavigate();
  const location = useLocation();
  const result = location.state.result; // 이전 페이지 결과 값

<<<<<<< HEAD
=======

>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
  const goMain = (e) =>{
    navigate('../');
  }

  const submitReadme = (e) =>{
<<<<<<< HEAD
    var tempData = [ // cf
      {
        "id": "a.md",
        "content": [
          "test1",
          "test2",
          "test3"
        ]
      },
      {
        "id": "b.md",
        "content": [
          "test1",
          "test2",
          "test3"
        ]
      },
      {
        "id": "c.md",
        "content": [
          "test1",
          "test2",
          "test3"
        ]
      }
    ];

    axios({
      method: "post",
      url: 'http://localhost:8090/mdZipFile',
      data: result,
      responseType: "arraybuffer",
      headers: {
        "Content-Type": "application/json",
      }
    })
    .then(function (response){
      const blob = new Blob([response.data], {type: "application/zip"});
=======
    const formData = new FormData();
    // hard coding
    formData.append('mdName', "A");
    formData.append('userName', "YeJi222");
    formData.append('repName', "SpringBoot_RSS_Local");

    axios({
      method: "post",
      data: formData,
      responseType: "text",
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(function (response){
      console.log("result : ", response.data);

      const blob = new Blob([response.data]);
>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
<<<<<<< HEAD
      link.download = "mdFiles.zip"; 
=======
      link.download = "A.md"; // 나중에 변수 쓰기
>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
      // 링크를 바디에 추가하고, 강제로 click 이벤트 발생시켜 파일 다운로드
      document.body.appendChild(link);
      link.click();
      link.remove();

      // 다운로드가 끝난 리소스(객체 URL)를 해제
      window.URL.revokeObjectURL(fileObjectUrl);
<<<<<<< HEAD
      
=======
>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
    })
    .catch(function(error){
      //handle error
      console.log(error);
    })
    .then(function(){
      // always executed
    });
  }

<<<<<<< HEAD
=======



>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
        </header>
        <div className="row">
          <div className="col-sm-12">
            <ReadmeFileResultList readmeList={result} />
          </div>
          <div className="col-sm-12 calign mb-3">
<<<<<<< HEAD
            <input type="button" className="bt-back" value="Full Download" onClick={submitReadme} />
=======
            <input type="button" className="bt-back" value="download" onClick={submitReadme} />
>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goMain} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Result;
