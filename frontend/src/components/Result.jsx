import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";

import ReadmeFileList from "./ReadmeFileList";
import ReadmeFileContent from "./ReadmeFileContent";

function Result(props) {
  const navigate = useNavigate();
  const location = useLocation();

  const Wrapper = styled.div`
      padding: 0;
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


        <div className="resultSection">
          <ReadmeFileList />

          <ReadmeFileContent readme={location.state.readme}/>
        </div>
        <input type="button" value="Back" onClick={navigate("./")}/>
      </Wrapper>
  );
}

export default Result;
