import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileSelect from "./File/ReadmeFileSelect";
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

  const [readmeObject, setReadmeObject] = useState(["## template", "# Contributors"]);
  const [currentReadme, setCurrentReadme] = useState("A");

  const index = location.state.index;
  const constrollerList = location.state.templateList;

  const goMain = (e) =>{
    navigate('../');
  }


  const generateReadme = (e) =>{
    navigate('../result', {
      state: {
        result: readmeObject
      }
    });
  }

  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
        </header>
        <div className="row">
          <div className="col-sm-3 mb-2">
            <ReadmeFileSelect currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
          </div>
          <div className="col-sm-9">
          </div>

          <div className="col-sm-8 mb-4">
            <ReadmeFileContent content={readmeObject} />
          </div>
          <div className="col-sm-4 mb-4">
            <Controller constrollerList={constrollerList} prevContent={readmeObject} setContent={setReadmeObject}/>
          </div>
          <div className="col-sm-12 calign mb-3">
            <input type="button" className="bt-back" value="Generate" onClick={generateReadme} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goMain} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Editor;
