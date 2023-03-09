import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import ReadmeFileList from "./ReadmeFileList";

function Result(props) {
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
        </header>

        <ReadmeFileList />
        <input type="button" value="Back" onClick={navigate("./")}/>
      </Wrapper>
  );
}

export default Result;
