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

  const [readmeObject, setReadmeObject] = useState(location.state.readmeObject);
  const [currentReadme, setCurrentReadme] = useState(readmeObject[0].id);
  const [forRelanderng, setForRelandering] = useState("");
  const [position, setPosition] = useState(1);


  let project_id = location.state.project_id;
  let controllerList = location.state.framework_list;

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

  const setContent = (e) =>{
    setReadmeObject(e);
    setForRelandering(forRelanderng + "1");
  }

  const deleteContent = (e) =>{
    var tempReadme = readmeObject;
    if(Number(position) === tempReadme.find(e => e.id === currentReadme).content.length){
      setPosition(position-1);
    }
    tempReadme.find(e => e.id === currentReadme).content.splice(e.target.value ,1);
    setReadmeObject(tempReadme);
    setForRelandering(forRelanderng + "1");
  }


  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
        </header>

        <div className="row">
          <div className="col-sm-3 mb-2">
            <ReadmeFileSelect readmeList={readmeObject} currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
          </div>
          <div className="col-sm-9">
          </div>

          <div className="col-sm-8 mb-4">
            <ReadmeFileContent
            title={currentReadme}
            content={readmeObject.find(e => e.id === currentReadme)}
            forRelanderng={forRelanderng}
            position={position}
            setPosition={setPosition}
            deleteContent={deleteContent}
            />
          </div>

          <div className="col-sm-4 mr-2 sideBanner">
            <Controller
              controllerList={controllerList}
              project_id={project_id}
              currentReadme={currentReadme}
              content={readmeObject}
              setContent={setContent}
              setPosition={setPosition}
              position={position}
            />
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
