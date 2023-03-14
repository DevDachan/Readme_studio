import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';


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
  const userName = useRef();
  const repName = useRef();
  const githubRepLink = useRef();

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
    formData.append('jsonParam1', userName.current.value);
    formData.append('jsonParam2', repName.current.value);


    console.log(formData);
    console.log(userName.current.value);
    console.log(repName.current.value);

    axios({
      method: "post",
      url: 'http://localhost:8090/readme',
      data: formData
    })
      .then(function (response){
        //handle success
        navigate('./result', {
          state: {
            readme: response.data,
            userName: userName,
            repName: repName
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
            <input type="text" name="email" id="email" ref={githubRepLink} placeholder="Github Repository Link" />
            <input type="submit" value="Generate" />
          </form>

          <form id="generate-form-files">
            <div className="row">
              <div className="col-sm-4">
                <input type="text" name="userName" id="user-name" ref={userName} placeholder="User Name"/>
              </div>
              <div className="col-sm-4">
                <input type="text" name="repName" id="rep-name" ref={repName}  placeholder="Repository Name"/>
              </div>

            <div className="col-sm-3">
            <input type="file" name="file" id="project-files" accept=".zip" onChange={getFile} style={{"display": "none"}}/>
            <label htmlFor="project-files" style={{"display":"inline", "marginRight": "20px"}}>
              <div id="file-selector" className={(fileSelected ? "fileSelected" : "fileNotSelected")}>{fileName}</div>
            </label>
            </div>
            <div className="col-sm-1">
              <input type="button" value="Generate" onClick={submitReadme}/>
            </div>
            </div>
          </form>
        </div>
      </Wrapper>
  );
}

export default Main;
