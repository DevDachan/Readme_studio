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
  const [userName, setUserName] = useState();
  const [repName, setRepName] = useState();
  const [githubRepLink, setGithubRepLink] = useState('');
  const [fileName, setFileName] = useState("Upload");
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
    //formData.append('rendering', "1"); // yeji

    var readme_list = [];
    axios({
      method: "post",
      url: 'http://localhost:8090/register',
      data: formData
    })
    .then(function (response){
      //handle success
      var defaultData = "<!-- empty_textarea -->\n"+
      "🚪 Stack : Spring boot    \n"+
      "🌠 Version:  "+ response.data.springBootVersion+"   \n"+
      "📕 Gruop ID : "+ response.data.groupId+"   \n"+
      "📘 Artifact ID : "+ response.data.artifactId+"   \n"+
      "📙 Java Version :"+ response.data.javaVersion+"   \n"+
      "📚 DB : "+ response.data.databaseName;

      readme_list.push({id: response.data.readmeName, content : [defaultData] , type : ["Default Data"]});

      navigate('./editor', {
        state: {
          project_id: 544931,
          framework_list: response.data.frameworkList,
          readmeObject:readme_list,
          defaultData: defaultData
        }
      });
    })
    .catch(function(error){
      //handle error
    })
    .then(function(){
      // always executed
    });
  }


  const linkSubmitReadme = (e) =>{

    const formData = new FormData();
    formData.append('jsonParam1', document.getElementById("repoLink").value);

    var readme_list = [];
    console.log(document.getElementById("repoLink").value);
    axios({
      method: "post",
      url: 'http://localhost:8090/register2',
      data: formData
    })
    .then(function (response){
      //handle success
      var defaultData = "<!-- empty_textarea -->\n"+
      "🚪 Stack : Spring boot    \n"+
      "🌠 Version:  "+ response.data.springBootVersion+"   \n"+
      "📕 Gruop ID : "+ response.data.groupId+"   \n"+
      "📘 Artifact ID : "+ response.data.artifactId+"   \n"+
      "📙 Java Version :"+ response.data.javaVersion+"   \n"+
      "📚 DB : "+ response.data.databaseName;

      readme_list.push({id: response.data.readmeName, content : [defaultData]});

      navigate('./editor', {
        state: {
          project_id: 544931,
          framework_list: response.data.frameworkList,
          readmeObject:readme_list,
          defaultData: defaultData
        }
      });
    })
    .catch(function(error){
      //handle error
    })
    .then(function(){
      // always executed
    });
  }

  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
          <p style={{backgroundColor:"transparent"}}>Project의 내용을 파싱해 자동으로 Readme파일을 작성해주는 서비스 입니다.<br />
          Github repository link 혹은 Project 파일을 업로드 해주세요</p>
        </header>

        <div>
          <form id="generate-form-git" method="post" action="#">
            <input type="text" autocomplete="off" name="email" className="ip-url" id="repoLink"
            placeholder="Github Repository Link" maxLength="100" />
            <input type="button" className="btn-3d blue" value="Generate" onClick={linkSubmitReadme}/>
          </form>

          <form id="generate-form-files">
            <div className="row">
              <div className="col-sm-3">
                <input type="text" name="userName" id="user-name" defaultValue={userName}
                  style={{
                    height: "3em"
                  }}
                required placeholder="User Name"/>
              </div>

              <div className="col-sm-3">
                <input type="text" name="repName" id="rep-name"
                  style={{
                    height: "3em"
                  }}
                defaultValue={repName} required placeholder="Repository Name"/>
              </div>

            <div className="col-sm-3">
            <input type="file" name="file" id="project-files" accept=".zip" onChange={getFile} style={{"display": "none"}}/>
            <label htmlFor="project-files" className="btn-inputfile">
              <div id="file-selector" >{fileName}</div>
            </label>
            </div>
            <div className="col-sm-3">
              <input type="button" className="btn-inputfile" value="Generate" onClick={submitReadme}/>
            </div>
            </div>
          </form>
        </div>
      </Wrapper>
  );
}

export default Main;
