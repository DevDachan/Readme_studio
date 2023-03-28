import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

function ReadmeFileResultContent(props) {
  const navigate = useNavigate();

  const item = props.currentReadme;
  const setItem = props.setCurrentReadme;
  const readmeList = props.readmeList;

  const selectList = e =>{
    setItem(e.target.value);
  }

  const downloadMD = (e, mdID, fileName) =>{
    console.log("mdID : ", fileName);

    axios({
      method: "post",
      url: 'http://localhost:8090/mdFile',
      data: readmeList[mdID],
      responseType: "arraybuffer",
      headers: {
        "Content-Type": "application/json",
      }
    })
    .then(function (response){
      const blob = new Blob([response.data]);
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
      link.download = fileName;
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
        <ul className="file-list">
          <li className="file-list-header"> README List </li>
          {
            readmeList.map((it, idx) => (
              <li onClick={(e) => {downloadMD(e, idx, it.id)}} className="file-list-item" key={it.id} value={it.id}> {it.id}</li>
            ))
          }

        </ul>
      </Wrapper>
  );
}

export default ReadmeFileResultContent;
