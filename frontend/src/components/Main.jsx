import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

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

  const [file, setFile] = useState();
  const [fileName, setFileName] = useState("Project Select");
  const [fileSelected, setFileSelected] = useState(false);

  const getFile = (e) =>{
    //e.preventDefault(); //prevent reload page
    if(e.target.files){
      const uploadFile = e.target.files[0];
      setFile(uploadFile);
      setFileName(uploadFile.name);
      setFileSelected(true);
    }
  }

  const submitReadme = (e) =>{
    const formData = new FormData();
    formData.append('file', file);

    axios({
      method: "post",
      url: 'http://localhost:8090/readme',
      data: formData
    })
      .then(function (response){
        //handle success
        console.log("response success");
        console.log(response.data);
        navigate('./result', {
          state: {
            readme: response.data,
            a: "hello"
          }
        });
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
          <p>Project의 내용을 파싱해 자동으로 Readme파일을 작성해주는 서비스 입니다.<br />
          Github repository link 혹은 Project 파일을 업로드 해주세요</p>
        </header>

        <div>
          <form id="generate-form-git" method="post" action="#">
            <input type="email" name="email" id="email" placeholder="Github Repository Link" />
            <input type="submit" value="Generate" />
          </form>

          <form id="generate-form-files">
            <input type="file" name="file" id="project-files" accept=".zip" onChange={getFile} style={{"display": "none"}}/>
            <label htmlFor="project-files" style={{"display":"inline", "marginRight": "20px"}}>
              <div id="file-selector" className={(fileSelected ? "fileSelected" : "fileNotSelected")}>{fileName}</div>
            </label>
            <input type="button" value="Generate" onClick={submitReadme}/>
          </form>
        </div>
      </Wrapper>
  );
}

export default Main;
