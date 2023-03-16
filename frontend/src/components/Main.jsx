import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';

const Wrapper = styled.div`
    padding: 0 2.5em;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;


function Main(props) {
  const navigate = useNavigate();
  const readmeFileList = useState();

  const [file, setFile] = useState();
  const [userName, setUserName] = useState('');
  const [repName, setRepName] = useState('');
  const [githubRepLink, setGithubRepLink] = useState('');
  const [fileName, setFileName] = useState("Project Select");
  const [fileSelected, setFileSelected] = useState(false);


  const getFile = (e) =>{
    //e.preventDefault(); //prevent reload page
    if(e.target.files){
      const uploadFile = e.target.files[0];
      setFile(uploadFile);
      setFileName(uploadFile.name);
      setFileSelected(true);
      if(document.getElementById("user-name").value !== userName){
        setUserName(document.getElementById("user-name").value)
      }
      if(document.getElementById("rep-name").value !== repName){
        setRepName(document.getElementById("rep-name").value)
      }

    }
  }

  const changeUserName = e =>{
    setUserName(e.target.value);
  };

  const changeRepName = e =>{
    setRepName(e.target.value);
  };

  const submitReadme = (e) =>{

    setRepName(document.getElementById("rep-name").value);
    setUserName(document.getElementById("user-name").value);
    setGithubRepLink(document.getElementById("email").value);
    if(document.getElementById("user-name").value !== userName){
      setUserName(document.getElementById("user-name").value)
    }
    if(document.getElementById("rep-name").value !== repName){
      setRepName(document.getElementById("rep-name").value)
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('jsonParam1', userName);
    formData.append('jsonParam2', repName);


    axios({
      method: "post",
      url: 'http://localhost:8090/register',
      data: formData
    })
      .then(function (response){
        //handle success
        navigate('./editor', {
          state: {
            index: 1,
            userName: userName,
            repName: repName
          }
        });
      })
      .catch(function(error){
        //handle error
        console.log(error);
        navigate('./editor', {
          state: {
            index: 1,
            userName: userName,
            repName: repName
          }
        });
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
            <input type="text" name="email" id="email" placeholder="Github Repository Link" />
            <input type="submit" value="Generate" />
          </form>

          <form id="generate-form-files">
            <div className="row">
              <div className="col-sm-4">
                <input type="text" name="userName" id="user-name" defaultValue={userName} placeholder="User Name"/>
              </div>

              <div className="col-sm-4">
                <input type="text" name="repName" id="rep-name" defaultValue={repName} placeholder="Repository Name"/>
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
