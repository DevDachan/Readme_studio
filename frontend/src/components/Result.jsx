/*

import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";



import ReadmeFileResultList from "./File/ReadmeFileResultList";
import ReadmeFileContent from "./File/ReadmeFileContent";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    min-width: 860px;
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

function Result(props) {
  const navigate = useNavigate();
  const location = useLocation();
  const result = location.state.result; // 이전 페이지 결과 값

  const goMain = (e) =>{
    navigate('../');
  }

  const goBack = (e) =>{
    navigate(-1);
  }

  const submitReadme = (e) =>{
    axios({
      method: "post",
      url: 'http://localhost:8090/mdZipFile',
      data: result,
      responseType: "arraybuffer",
      headers: {
        "Content-Type": "application/json",
      }
    })
    .then(function (response){
      const blob = new Blob([response.data], {type: "application/zip"});
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
      link.download = "mdFiles.zip";
      // 링크를 바디에 추가하고, 강제로 click 이벤트 발생시켜 파일 다운로드
      document.body.appendChild(link);
      link.click();
      link.remove();

      // 다운로드가 끝난 리소스(객체 URL)를 해제
      window.URL.revokeObjectURL(fileObjectUrl);
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
          <div className="col-sm-12">
            <ReadmeFileResultList readmeList={result} />
          </div>
          <div className="col-sm-12 calign mb-3">
            <input type="button" className="bt-down" value="Full Download" onClick={submitReadme} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-main" value="Main" onClick={goMain} />
          </div>
          <div className="col-sm-12 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goBack} />
          </div>
        </div>
      </Wrapper>
  );
}

export default Result;*/


import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileContent from "./File/ReadmeFileContent";

import ReadmeFileResultList from "./File/ReadmeFileResultList";

import Palette from "./Palette/Palette";
import Modal from "react-bootstrap/Modal";


const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

function Result(props) {
  const navigate = useNavigate();
  const location = useLocation();

  const [readmeObject, setReadmeObject] = useState(location.state.result);
  const [currentReadme, setCurrentReadme] = useState(readmeObject[0].id);
  const [forRelanderng, setForRelandering] = useState("");


  let project_id = location.state.project_id;

  const result = location.state.result; // 이전 페이지 결과 값

  const goBack = (e) =>{
    navigate('../editor', {
      state: {
        project_id: project_id,
        framework_list: location.state.paletteList,
        readmeObject:readmeObject,
        defaultData: location.state.project_detail
      }
    });
  }

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


  const deleteReadme = (e) => {
    var temp = readmeObject;
    if(temp.length == 1 ){
      temp = [{id: "README.md", content : [""]}];
      setReadmeObject(temp);
      setCurrentReadme(temp[0].id);
    }else{
      temp= temp.filter((e) => e.id !== currentReadme);
      setReadmeObject(temp);
      setCurrentReadme(temp[0].id);
    }
  }


  const submitReadme = (e) =>{
    axios({
      method: "post",
      url: 'http://localhost:8090/mdZipFile',
      data: result,
      responseType: "arraybuffer",
      headers: {
        "Content-Type": "application/json",
      }
    })
    .then(function (response){
      const blob = new Blob([response.data], {type: "application/zip"});
      // blob 사용하여 객체 URL 생성
      const fileObjectUrl = window.URL.createObjectURL(blob);
      // blob 객체 URL을 설정할 링크 만들기
      const link = document.createElement("a");
      link.href = fileObjectUrl;
      link.style.display = "none";

      // 다운로드 파일 이름 지정
      link.download = "mdFiles.zip";
      // 링크를 바디에 추가하고, 강제로 click 이벤트 발생시켜 파일 다운로드
      document.body.appendChild(link);
      link.click();
      link.remove();

      // 다운로드가 끝난 리소스(객체 URL)를 해제
      window.URL.revokeObjectURL(fileObjectUrl);
    })
    .catch(function(error){
      //handle error
      console.log(error);
    })
    .then(function(){
      // always executed
    });
  }


  //--------------------------------------------------------------------
  return (
      <Wrapper>
        <header id="editor-header">
          <img src="/images/logo.png" className="logo-image"/>
          <h1 className="title-text">README STUDIO</h1>
          <h2 className="subtitle-text">README GENERATION WEB SERVICE</h2>
        </header>

        <section>
          <article>
            <div className="row">
              <div className="col-sm-10 ralign mb-2">
              </div>
              <div className="col-sm-2 ralign mb-2">
                <input type="button" className="btn-3d red bt-back" value="" onClick={goBack} />
              </div>
              <div className="col-sm-12 mb-4">
                <div className="editorDiv">
                  <ReadmeFileResultList
                    currentReadme={currentReadme}
                    setCurrentReadme={setCurrentReadme}
                    readmeList={readmeObject}
                    submitReadme={submitReadme}
                  />
                  <div className="resultDiv">
                    <div className="row">
                      <div className="col-sm-12 mt-2 mb-4 lalign">
                        <h3 className="result-readmeName"> {currentReadme} </h3>
                      </div>
                      <div className="col-sm-12">
                        <ReadmeFileContent
                          content={readmeObject.find(e => e.id === currentReadme)}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </article>
        </section>
      </Wrapper>
  );
}

export default Result;
