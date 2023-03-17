import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

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

  const [readmeObject, setReadmeObject] = useState();
  const [currentReadme, setCurrentReadme] = useState("A");

  const index = location.index;
  const constrollerList = location.templateList;

  const goMain = (e) =>{
    navigate('../');
  }


  const getTemplate = (e) =>{

    const formData = new FormData();
    formData.append('index', index);
    formData.append('template_id', e.number);

    axios({
      method: "get",
      url: 'http://localhost:8090/plzTemplate',
      data: formData
    })
    .then(function (response){
      //handle success
      setReadmeObject(response);
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
          <div className="col-sm-3 mb-2">
            <ReadmeFileList currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
          </div>

          <div className="col-sm-9">
          </div>

          <div className="col-sm-8 mb-4">
            <ReadmeFileContent />
          </div>
          <div className="col-sm-4 mb-4">
            <Controller />
          </div>
          <div className="col-sm-12 calign mb-3">
            <input type="button" className="bt-back" value="Generate" onClick={goMain} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goMain} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Editor;
