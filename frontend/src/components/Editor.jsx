import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

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
  const project_id = location.state.project_id;
  const paletteList = location.state.framework_list;
  const project_detail = location.state.defaultData;

  const [show, setShow] = useState(false);

  const previewChange = () => {
    setShow(show == true ? false : true);
  }

  const goMain = (e) =>{
    navigate('../');
  }

  const goResult = (e) =>{
    navigate('../result', {
      state: {
        result: readmeObject,
        project_id: project_id,
        paletteList:paletteList,
        project_detail:project_detail
      }
    });
  }
  //-----------------------  set README Object with relandering  ---------------------------------------------
  const setContent = (e) =>{
    setReadmeObject(e);
    setForRelandering(forRelanderng + "1");
  }

  //--------------------------------------------------------------------
  const deleteContent = (e) =>{
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    if(Number(position) === tempReadme.find(e => e.id === currentReadme).content.length){
      setPosition(position-1);
    }
    tempReadme.find(e => e.id === currentReadme).content.splice(e.target.value ,1);
    tempReadme.find(e => e.id === currentReadme).type.splice(e.target.value ,1);

    setReadmeObject(tempReadme);
    setForRelandering(forRelanderng + "1");
  }
  //----------------------------------------------------------------------

  const pasteContent = (e) => {
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    tempReadme.find(e => e.id === currentReadme).content.splice(e.target.value,0, tempReadme.find(e => e.id === currentReadme).content[e.target.value]);
    tempReadme.find(e => e.id === currentReadme).type.splice(e.target.value,0, tempReadme.find(e => e.id === currentReadme).type[e.target.value]);
    setReadmeObject(tempReadme);
    setPosition(Number(e.target.value)+2);
    setForRelandering(forRelanderng + "1");
  }


  //---------------------------------------------------------------------------------
  const changePosition = (e) =>{
    var currentIndex = Number(e.target.name);

    if(e.target.id.includes("Down")){
      if(currentIndex < readmeObject.find(e => e.id === currentReadme).content.length-1 ){
        var content = readmeObject.find(e => e.id === currentReadme).content;
        var type = readmeObject.find(e => e.id === currentReadme).type;

        var changeIndex = currentIndex+1;
        var cur_temp_content = content.splice(currentIndex,1)[0];
        var cur_temp_type = type.splice(currentIndex,1);

        content.splice(changeIndex, 0, cur_temp_content);
        type.splice(changeIndex, 0, cur_temp_type);

        setReadmeObject(readmeObject);
        setForRelandering(forRelanderng + "1");
      }
    }else{
      if(currentIndex !== 0){
        var content = readmeObject.find(e => e.id === currentReadme).content;
        var type = readmeObject.find(e => e.id === currentReadme).type;
        var changeIndex = currentIndex-1;

        //delete currentIndex item and copy
        var cur_temp_content = content.splice(currentIndex,1)[0];
        var cur_temp_type = type.splice(currentIndex,1);

        content.splice(changeIndex, 0, cur_temp_content);
        type.splice(changeIndex, 0, cur_temp_type);

        setReadmeObject(readmeObject);
        setForRelandering(forRelanderng + "1");
      }
    }
  }

  //--------------------------------------------------------------------
  const changeTextArea = (e,i) =>{
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    var content = e;
    var position_id = i.target.parentElement.parentElement.parentElement.parentElement.id;
    var position = Number(position_id.substr(10,3));
    tempReadme.find(e => e.id === currentReadme).content[position] = "<!-- empty_textarea -->\n" + content;
    setContent(tempReadme);
  }

  const changeLicense = (e,i) =>{
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    var content = e;
    var position_id = i.target.parentElement.parentElement.parentElement.parentElement.id;
    var position = Number(position_id.substr(10,3));
    tempReadme.find(e => e.id === currentReadme).content[position] = "## License\n" + content;
    setContent(tempReadme);
  }

  const changeArchitecture = (e,i) =>{
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    var content = e;
    var position_id = i.target.parentElement.parentElement.parentElement.parentElement.id;
    var position = Number(position_id.substr(10,3));
    tempReadme.find(e => e.id === currentReadme).content[position] = "## Project Architecture (Tree Structure)<br> <!-- Project Architecture -->" + content;
    setContent(tempReadme);
  }

  const changePeriod = (e) => {
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    var position = e.target.name;
    const valueData = e.target.value;

    const formData = new FormData();
    var temp = tempReadme.find(e => e.id === currentReadme).content[position];

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

    }else{
      formData.append('start_date', "no");
      formData.append('end_date', "no");
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
    });
  }

  //--------------------------------------------------------------------------------------


  const addReadme = (e) => {
    setReadmeObject([...readmeObject, {id: "README"+readmeObject.length+".md", content : [project_detail] , type : ["Default Data"] }]);
    setCurrentReadme("README"+readmeObject.length+".md");
  }



  const deleteReadme = (e) => {
    var tempReadme = JSON.parse(JSON.stringify(readmeObject));
    if(tempReadme.length == 1 ){
      tempReadme = [{id: "README.md", content : [project_detail], type : ["Default Data"] }];
      setReadmeObject(tempReadme);
      setCurrentReadme(tempReadme[0].id);
    }else{
      tempReadme= tempReadme.filter((e) => e.id !== currentReadme);
      setReadmeObject(tempReadme);
      setCurrentReadme(tempReadme[0].id);
    }
  }
  //--------------------------------------------------------------------
  return (
      <Wrapper>
        <header id="editor-header">
          <img src="/images/logo.png" className="logo-image" onClick={goMain}/>
          <h1 className="title-text" onClick={goMain} >README STUDIO</h1>
          <h2 className="subtitle-text">README GENERATION WEB SERVICE</h2>
        </header>

        <section>
          <article>
              <div className="row" style={{minWidth: "1100px"}}>
                <div className="col-sm-2 lalign mb-2">
                  <input type="button" className="bt-back" value="" onClick={goMain} />
                </div>
                <div className="col-sm-10 ralign mb-2">
                </div>
                <div className="col-sm-12 mb-4">
                  <div className="editorDiv">
                    <Palette
                      paletteList={paletteList}
                      project_id={project_id}
                      currentReadme={currentReadme}
                      content={readmeObject}
                      position={position}

                      setContent={setContent}
                      setPosition={setPosition}

                      forRelanderng={forRelanderng}
                    />

                    <ReadmeFileComponent
                      //for content variable
                      title={currentReadme}
                      curreadme={readmeObject.find(e => e.id === currentReadme)}
                      position={position}

                      // for contents action
                      setContent={setContent}
                      setPosition={setPosition}

                      changePosition={changePosition}
                      changeTextArea={changeTextArea}
                      changePeriod={changePeriod}
                      changeLicense={changeLicense}
                      changeArchitecture={changeArchitecture}

                      deleteContent={deleteContent}
                      pasteContent={pasteContent}
                      deleteReadme={deleteReadme}

                      //for Header variable
                      currentReadme={currentReadme}
                      readmeList={readmeObject}

                      //for Header action
                      setCurrentReadme={setCurrentReadme}
                      addReadme={addReadme}

                      goResult={goResult}
                      previewChange={previewChange}

                      forRelanderng={forRelanderng}
                    />
                  <Modal className="modal-lg" show={show} onHide={previewChange}>
                    <Modal.Header>
                      <Modal.Title>README Preview</Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                      <ReadmeFileContent
                        content={readmeObject.find(e => e.id === currentReadme)}
                      />
                    </Modal.Body>

                    <Modal.Footer>
                      <button className="bt-close" onClick={previewChange}>Close</button>
                    </Modal.Footer>
                  </Modal>
                </div>
              </div>
          </div>
        </article>
      </section>
      </Wrapper>
  );
}

export default Editor;
