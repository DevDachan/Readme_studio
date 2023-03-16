import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";

import ReadmeFileList from "./File/ReadmeFileList";
import ReadmeFileContent from "./File/ReadmeFileContent";
import Controller from "./Controller/Controller";


const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;


function Editor(props) {
  const navigate = useNavigate();
  const location = useLocation();

  const goMain = (e) =>{
    navigate('../');
  }

  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
        </header>
        <div className="row">
          <div className="col-sm-3 mb-2">
            <ReadmeFileList />
          </div>

          <div className="col-sm-9">
          </div>

          <div className="col-sm-8">
            <ReadmeFileContent />
          </div>
          <div className="col-sm-4">
            <Controller />
          </div>
          <div className="col-sm-12">
            <input type="button" className="bt-back" value="Back" onClick={goMain} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Editor;
