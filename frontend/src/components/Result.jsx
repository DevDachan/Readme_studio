import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileResultList from "./File/ReadmeFileResultList";
import ReadmeFileContent from "./File/ReadmeFileContent";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    min-width: 860px;
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

function Result(props) {
  const navigate = useNavigate();
  const location = useLocation();
  const result = location.state.result; // 이전 페이지 결과 값

  const goMain = (e) =>{
    navigate('../');
  }

  const goBack = (e) =>{
    navigate(-1);
  }

  const submitReadme = (e) =>{
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
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
      link.download = "mdFiles.zip";
      // 링크를 바디에 추가하고, 강제로 click 이벤트 발생시켜 파일 다운로드
      document.body.appendChild(link);
      link.click();
      link.remove();

      // 다운로드가 끝난 리소스(객체 URL)를 해제
      window.URL.revokeObjectURL(fileObjectUrl);
    })
    .catch(function(error){
      //handle error
      console.log(error);
    })
    .then(function(){
      // always executed
    });
  }

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
            <input type="button" className="bt-down" value="Full Download" onClick={submitReadme} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-main" value="Main" onClick={goMain} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goBack} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Result;
