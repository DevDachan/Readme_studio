import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileSelect from "./File/ReadmeFileSelect";
import ReadmeFileContent from "./File/ReadmeFileContent";
import ReadmeFileComponent from "./File/ReadmeFileComponent";
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

function Editor(props) {
  const navigate = useNavigate();
  const location = useLocation();

  const [readmeObject, setReadmeObject] = useState(location.state.readmeObject);
  const [currentReadme, setCurrentReadme] = useState(readmeObject[0].id);
  const [forRelanderng, setForRelandering] = useState("");
  const [position, setPosition] = useState(1);
  const handleClose = () => setShow(false);
  const handleOpen = () => setShow(true);
  const [show, setShow] = useState(false);

  let project_id = location.state.project_id;
  let paletteList = location.state.framework_list;
  let project_detail = location.state.defaultData;



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
  //--------------------------------------------------------------------
  const setContent = (e) =>{
    setReadmeObject(e);
    setForRelandering(forRelanderng + "1");
  }
  //--------------------------------------------------------------------
  const deleteContent = (e) =>{
    var tempReadme = readmeObject;
    if(Number(position) === tempReadme.find(e => e.id === currentReadme).content.length){
      setPosition(position-1);
    }
    tempReadme.find(e => e.id === currentReadme).content.splice(e.target.value ,1);
    setReadmeObject(tempReadme);
    setForRelandering(forRelanderng + "1");
  }
  //--------------------------------------------------------------------
  const changePosition = (e) =>{
    var currentIndex = Number(e.target.name);

    if(e.target.id.includes("Down")){
      if(currentIndex < readmeObject.find(e => e.id === currentReadme).content.length-1 ){
        var content = readmeObject.find(e => e.id === currentReadme).content;
        var changeIndex = currentIndex+1;
        //delete currentIndex item and copy
        var chan_temp = Object.assign(content[changeIndex]);
        var cur_temp = content.splice(currentIndex,1)[0];

        content.splice(changeIndex, 0, cur_temp);

        setReadmeObject(readmeObject);
        setForRelandering(forRelanderng + "1");
      }
    }else{
      if(currentIndex !== 0){
        var content = readmeObject.find(e => e.id === currentReadme).content;
        var changeIndex = currentIndex-1;

        //delete currentIndex item and copy
        var chan_temp = Object.assign(content[changeIndex]);
        var cur_temp = content.splice(currentIndex,1)[0];

        content.splice(changeIndex, 0, cur_temp);

        setReadmeObject(readmeObject);
        setForRelandering(forRelanderng + "1");
      }
    }
  }

  //--------------------------------------------------------------------
  const changeTextArea = (e,i) =>{
    let tempReadme = readmeObject;
    var content = e;
    var position_id = i.target.parentElement.parentElement.parentElement.parentElement.id;
    var position = Number(position_id.substr(10,3));
    tempReadme.find(e => e.id === currentReadme).content[position] = "<!-- empty_textarea -->\n" + content;

    setContent(tempReadme);
  }
  //--------------------------------------------------------------------
  const changePeriod = (e) => {
    let tempReadme = readmeObject;
    var position = e.target.name;
    const valueData = e.target.value;
    var temp = "";
    const formData = new FormData();

    temp = tempReadme.find(e => e.id === currentReadme).content[position];

    if(e.target.id.includes("period_start")){
      if(temp.includes("End Date") || !temp.split('id="end_date">')[1].split("</span>")[0].includes("2")){
        formData.append('end_date', "no");
      }else{
        formData.append('end_date', temp.split('id="end_date">')[1].split("</span>")[0]);
      }

      if(valueData == ""){
        formData.append('start_date', "no");
        document.getElementById("period_end"+position).value = "";
      }else{
        formData.append('start_date', valueData);
      }

      axios({
        method: "post",
        url: 'http://localhost:8090/editPeriod',
        data: formData
      })
      .then(function (response){
        tempReadme.find(e => e.id === currentReadme).content[position] = response.data;
        setContent(tempReadme);
      })
      .catch(function(error){
      })
      .then(function(){
      });

    }else if(e.target.id.includes("period_end")){
      if(valueData == ""){
        formData.append('end_date', "no");
      }else{
        formData.append('end_date', valueData);
      }

      if(temp.includes("Start Date") || !temp.split('id="start_date">')[1].split("</span>")[0].includes("2")){
        formData.append('start_date', "no");
        document.getElementById("period_end"+position).value = "";
      }else{
        formData.append('start_date', temp.split('id="start_date">')[1].split("</span>")[0]);
      }

      axios({
        method: "post",
        url: 'http://localhost:8090/editPeriod',
        data: formData
      })
      .then(function (response){
        tempReadme.find(e => e.id === currentReadme).content[position] = response.data;
        setContent(tempReadme);
      })
      .catch(function(error){
      })
      .then(function(){
        // always executed
      });

    }else{
      formData.append('start_date', "no");
      formData.append('end_date', "no");
      axios({
        method: "post",
        url: 'http://localhost:8090/editPeriod',
        data: formData
      })
      .then(function (response){
        tempReadme.find(e => e.id === currentReadme).content[position] = response.data;
        setContent(tempReadme);
      })
      .catch(function(error){
      })
      .then(function(){
        // always executed
      });
    }
  }

  const addReadme = (e) => {
    setReadmeObject([...readmeObject, {id: "README"+readmeObject.length+".md", content : [project_detail]}]);
  }

  const deleteReadme = (e) => {
    var temp = readmeObject;
    if(temp.length == 1 ){
      temp = [{id: "README.md", content : [project_detail]}];
      setReadmeObject(temp);
      setCurrentReadme(temp[0].id);
    }else{
      temp= temp.filter((e) => e.id !== currentReadme);
      setReadmeObject(temp);
      setCurrentReadme(temp[0].id);
    }
  }
  //--------------------------------------------------------------------
  return (
      <Wrapper>
        <header id="header">
          <h1 className="header-text">Readme Generate</h1>
        </header>
        <section>
          <article>
              <div className="row">
                <div className="col-sm-3 mb-2">
                  <br/>
                  <ReadmeFileSelect readmeList={readmeObject} currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>

                </div>
                <div className="col-sm-2 calign mb-3">
                  <input type="button" className="btn-3d green bt-add" value="Add" onClick={addReadme} />
                </div>

                <div className="col-sm-2 calign mb-3">
                  <input type="button" className="bt-generate btn-3d blue" value="Generate" onClick={generateReadme} />
                </div>
                <div className="col-sm-2 calign mb-2">
                  <input type="button" className="btn-3d red bt-back" value="Back" onClick={goMain} />
                </div>
                <div className="col-sm-1">
                </div>
                <div className="col-sm-9 mb-4">
                  <ReadmeFileComponent
                  title={currentReadme}
                  project_id={project_id}
                  content={readmeObject.find(e => e.id === currentReadme)}
                  changePosition={changePosition}
                  forRelanderng={forRelanderng}
                  position={position}
                  setContent={setContent}
                  setPosition={setPosition}
                  deleteContent={deleteContent}
                  changeTextArea={changeTextArea}
                  changePeriod={changePeriod}
                  handleOpen={handleOpen}
                  deleteReadme={deleteReadme}
                  />
                <Modal className="modal-lg" show={show} onHide={handleClose}>
                  <Modal.Header>
                    <Modal.Title>README Preview</Modal.Title>
                  </Modal.Header>
                  <Modal.Body>
                    <ReadmeFileContent
                      content={readmeObject.find(e => e.id === currentReadme)}
                    />
                  </Modal.Body>
                  <Modal.Footer>
                    <button className="bt-close" onClick={handleClose}>닫기</button>
                  </Modal.Footer>
                </Modal>
            </div>
            <aside>
              <div className="sideBanner">
                <Palette
                  paletteList={paletteList}
                  project_id={project_id}
                  currentReadme={currentReadme}
                  content={readmeObject}
                  setContent={setContent}
                  setPosition={setPosition}
                  position={position}
                />
              </div>
            </aside>
          </div>
        </article>
      </section>
      </Wrapper>
  );
}

export default Editor;
