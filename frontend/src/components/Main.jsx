import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.css';
import Loading from "./Loading";


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
  const [loadingCheck, setLoadingCheck] = useState(false);
  const { cancel, token } = axios.CancelToken.source();

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
    document.getElementById("alertUserName").classList.remove( "p-alert-show");
    setUserName(e.target.value);
  };

  const changeRepName = e =>{
    document.getElementById("alertRepName").classList.remove( "p-alert-show");
    setRepName(e.target.value);
  };

  const changeLink = e =>{
    document.getElementById("alertLink").classList.remove( "p-alert-show");
  }

  const cancelLoading = e => {
    setLoadingCheck(false);
    cancel("cancel");
  }

  const submitReadme = (e) =>{
    if(userName == undefined ||  userName == ""){
      document.getElementById("alertUserName").classList.add( "p-alert-show");
      return;
    }else if(repName == undefined || repName == ""){
      document.getElementById("alertRepName").classList.add( "p-alert-show");
      return;
    }


    const formData = new FormData();
    formData.append('file', file);
    formData.append('jsonParam1', userName);
    formData.append('jsonParam2', repName);

    var readmeList = [];
    setLoadingCheck(true);
    axios({
      method: "post",
      url: 'http://localhost:8090/register',
      data: formData,
      cancelToken: token
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

      readmeList.push({projectId : response.data.projectId, id: "README.md", content : [defaultData] , type : ["Default Data"]});

      navigate('./editor', {
        state: {
          projectId: response.data.projectId,
          frameworkList: response.data.frameworkList,
          readmeObject:readmeList,
          defaultData: defaultData
        }
      });
    })
    .catch(function(error){
      cancelLoading();
    });
  }


  const linkSubmitReadme = (e) =>{
    const githubPattern = /^https:\/\/github\.com\//;

    if(document.getElementById("repoLink").value == ""){
      document.getElementById("alertLink").classList.add( "p-alert-show");
      return;
    }else if (!githubPattern.test(document.getElementById("repoLink").value)) {
      document.getElementById("alertLink").classList.add( "p-alert-show");
      return;
    }

    const formData = new FormData();
    formData.append('jsonParam1', document.getElementById("repoLink").value);
    var readmeList = [];

    setLoadingCheck(true);
    axios({
      method: "post",
      url: 'http://localhost:8090/register2',
      data: formData
    })
    .then(function (response){
      if(response.data.error !== "LinkFormatError" && response.data.error !== "cloneError"){
        //handle success
        var defaultData = "<!-- empty_textarea -->\n"+
        "🚪 Stack : Spring boot    \n"+
        "🌠 Version:  "+ response.data.springBootVersion+"   \n"+
        "📕 Gruop ID : "+ response.data.groupId+"   \n"+
        "📘 Artifact ID : "+ response.data.artifactId+"   \n"+
        "📙 Java Version :"+ response.data.javaVersion+"   \n"+
        "📚 DB : "+ "MariaDB";

        readmeList.push({projectId : response.data.projectId, id: "README.md", content : [defaultData] , type : ["Default Data"]});

        navigate('./editor', {
          state: {
            projectId: response.data.projectId,
            frameworkList: response.data.frameworkList,
            readmeObject:readmeList,
            defaultData: defaultData
          }
        });
      }
    })
    .catch(function(error){
      cancelLoading();
    });
  }


  const html = document.documentElement;
  const body = document.body;

  const height = Math.max(html.clientHeight, html.scrollHeight, html.offsetHeight,
                          body.clientHeight, body.scrollHeight, body.offsetHeight);

  const width = Math.max(html.clientWidth, html.scrollWidth, html.offsetWidth,
                         body.clientWidth, body.scrollWidth, body.offsetWidth);

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
                onChange={changeLink}
              />
              </div>
              <div className="col-sm-2">
                <input type="button" className="btn-generate" value="Generate" onClick={linkSubmitReadme}/>
              </div>
              <div className="col-sm-10 mt-3">
                <p id="alertLink" className="p-alert"> Please enter a Github Link </p>
              </div>
          </div>

          {loadingCheck?
            <div className="loading-container" style={{height: height, width:width}}>
            <Loading
              type="spin"
              message={"프로젝트 정보를 불러오고 있습니다."}
              cancelLoading={cancelLoading}
            />
            </div>
             : ""}

             </div>
           </Wrapper>
       );
     }
/*
          <form id="generate-form-files">
            <div className="row">

              <div className="col-sm-3">
                <input type="text" name="userName" id="user-name" defaultValue={userName}
                required onChange={changeUserName} placeholder="User Name"/>
              </div>

              <div className="col-sm-3">
                <input type="text" name="repName" id="rep-name" defaultValue={repName}
                required  onChange={changeRepName}  placeholder="Repository Name"/>
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
              <div className="col-sm-3">
                <p id="alertUserName" className="p-alert"> Enter a UserName!</p>
              </div>
              <div className="col-sm-3">
                <p id="alertRepName" className="p-alert"> Enter a RepName! </p>
              </div>
            </div>
          </form>
        </div>
      </Wrapper>
  );
}*/

export default Main;
