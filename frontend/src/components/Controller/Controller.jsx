import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
`;

const submitContributor = (e) =>{
  const formData = new FormData();
  // hard coding
  formData.append('mdName', "A");
  formData.append('userName', "YeJi222");
  formData.append('repName', "SpringBoot_RSS_Local");

  axios({
    method: "post",
    url: '/markdown',
    data: formData,
    responseType: "text",
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
    .then(function (response){
      console.log("result : ", response.data);
      //handle success
      /*
      navigate('./result', {
        state: {
          readme: response.data,
          userName: userName,
          repName: repName
        }
      });
      */
      
      const blob = new Blob([response.data]);
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
      link.download = "A.md"; // 나중에 변수 쓰기
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

function ReadmeFileContent(props) {
  const navigate = useNavigate();

  return (
      <Wrapper>
        <div className="controller-div row">
          <h3 className="controoler-title"> Controller</h3>
          <input type="button" className="mb-2" value="Template" />
          <input type="button" className="mb-2" value="Contributor" onClick={submitContributor}/>
        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
