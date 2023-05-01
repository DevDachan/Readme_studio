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
    min-height: 700px;
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


  const goMain = (e) =>{
    navigate('./');
  }



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
    // 혹시 relandering으로 인한 data 손실 우려
    if(document.getElementById("user-name").value !== userName){
      setUserName(document.getElementById("user-name").value);
    }
    if(document.getElementById("rep-name").value !== repName){
      setRepName(document.getElementById("rep-name").value);
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('jsonParam1', userName);
    formData.append('jsonParam2', repName);

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
          project_id: response.data.project_id,
          framework_list: response.data.frameworkList,
          readmeObject:readme_list,
          defaultData: defaultData
        }
      });
    })
    .catch(function(error){
      //handle error
    });
  }


  const linkSubmitReadme = (e) =>{

    const formData = new FormData();
    formData.append('jsonParam1', document.getElementById("repoLink").value);
    var readme_list = [];
    axios({
      method: "post",
      url: 'http://localhost:8090/register2',
      data: formData
    })
    .then(function (response){
      console.log(response.data.error);
      if(response.data.error !== "LinkFormatError" && response.data.error !== "cloneError"){
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
            project_id: response.data.project_id,
            framework_list: response.data.frameworkList,
            readmeObject:readme_list,
            defaultData: defaultData
          }
        });
      }
    })
    .catch(function(error){
      //handle error
    });
  }

  return (
      <Wrapper>
        <header id="main-header">
          <img src="/images/logo.png" className="logo-image" onClick={goMain}/>
          <h1 className="title-text" onClick={goMain} >README STUDIO</h1>
          <h2 className="subtitle-text">README GENERATION WEB SERVICE</h2>
        </header>

        <div>
          <div className="row mb-4">
            <div className="col-sm-10">
              <input type="text"
                id="repoLink"
                name="email"
                className="ip-url"
                autocomplete="off"
                placeholder="Github Repository Link"
                maxLength="100"
              />
              </div>
              <div className="col-sm-2">
                <input type="button" className="btn-generate" value="Generate" onClick={linkSubmitReadme}/>
              </div>
          </div>


          <form id="generate-form-files">
            <div className="row">

              <div className="col-sm-3">
                <input type="text" name="userName" id="user-name" defaultValue={userName}
                required placeholder="User Name"/>
              </div>

              <div className="col-sm-3">
                <input type="text" name="repName" id="rep-name"
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
