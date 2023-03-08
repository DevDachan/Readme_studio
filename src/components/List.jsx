import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


function Main(props) {
  const navigate = useNavigate();

  const readmeFileList = useState();

  const Wrapper = styled.div`
      padding: 0 2.5em;
      margin: 0 auto;
      width: calc(100% - 32px);
      display: flex;
      flex-direction: column;
      justify-content: center;
  `;

  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
          <p>Project의 내용을 파싱해 자동으로 Readme파일을 작성해주는 서비스 입니다.<br />
          Github repository link 혹은 Project 파일을 업로드 해주세요</p>
        </header>


        <form id="generate-form-git" method="post" action="#">
          <input type="email" name="email" id="email" placeholder="Github Repository Link" />
          <input type="submit" value="Generate" />
        </form>

        <form id="generate-form-files" method="post" action="#">
          <input type="file" name="files" id="project-files" multiple/>
          <input type="submit" value="Generate" />
        </form>
      </Wrapper>
  );
}

export default Main;
