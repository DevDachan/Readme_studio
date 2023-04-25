import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

const Wrapper = styled.div`
  padding: 30px;
  margin: 0;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  overflow:auto;
  border-right: 2px solid #D1D5DB;
`;

function ReadmeFileResultContent(props) {
  const navigate = useNavigate();
  const item = props.currentReadme;
  const setItem = props.setCurrentReadme;
  const readmeList = props.readmeList;
  const list = [];
  const downReadme = props.downReadme;


  const downloadMD = (e, mdID, fileName) =>{
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
    });
  }

  const chageMd = (e, mdId) =>{
    setItem(e.target.value);
  }

  return (
      <Wrapper>
        <h3 className="palette-optionsTitle"> Results</h3>
        <input type="button" className="bt-download" value="Download MD Files" onClick={downReadme} />
        <div className="palette-div row">
          <h3 className="palette-title"> MD LIST</h3>
          {
            readmeList.map((it, idx) => (
              <input type="button" className="mb-2 btn-palette" key={it.id} onClick={(e) => {chageMd(e, idx)}} value={it.id}/>
            ))
          }
        </div>
      </Wrapper>
  );
}

export default ReadmeFileResultContent;
