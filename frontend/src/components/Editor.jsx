import React, { useState } from "react";
import { useNavigate, useLocation  } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

import ReadmeFileSelect from "./File/ReadmeFileSelect";
import ReadmeFileContent from "./File/ReadmeFileContent";
import Palette from "./Palette/Palette";


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


  let project_id = location.state.project_id;
  let paletteList = location.state.framework_list;

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

  const setContent = (e) =>{
    setReadmeObject(e);
    setForRelandering(forRelanderng + "1");
  }

  const deleteContent = (e) =>{
    var tempReadme = readmeObject;
    if(Number(position) === tempReadme.find(e => e.id === currentReadme).content.length){
      setPosition(position-1);
    }
    tempReadme.find(e => e.id === currentReadme).content.splice(e.target.value ,1);
    setReadmeObject(tempReadme);
    setForRelandering(forRelanderng + "1");
  }

  const changePosition = (e) =>{
    var content = readmeObject.find(e => e.id === currentReadme).content;
    var currentIndex = Number(e.target.name);
    var changeIndex = Number(e.target.value-1);

    //delete currentIndex item and copy
    var chan_temp = Object.assign(content[changeIndex]);
    var cur_temp = content.splice(currentIndex,1);
    content.splice(changeIndex, 0, cur_temp);

    /* this line for change position*/
    //insert changeIndex item into currentIndex
    //content.splice(currentIndex,0, chan_temp);
    // delete changeIndex item
    //content.splice(changeIndex, 1);
    // insert currentIndex copy item into changeIndex
    //content.splice(changeIndex, 0, cur_temp);

    setReadmeObject(readmeObject);
    setForRelandering(forRelanderng + "1");
  }

  const changeTextArea = (e) =>{
    var position = e.target.name;
    let tempReadme = readmeObject;
    var content = e.target.value;

    tempReadme.find(e => e.id === currentReadme).content[position] = "<!-- empty_textarea -->\n" + content;

    setContent(tempReadme);
  }



  return (
      <Wrapper>
        <header id="header">
          <h1>Readme Generate</h1>
        </header>

        <div className="row">
          <div className="col-sm-3 mb-2">
            <ReadmeFileSelect readmeList={readmeObject} currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
          </div>
          <div className="col-sm-3 calign mb-3">
            <input type="button" className="bt-generate" value="Generate" onClick={generateReadme} />
          </div>
          <div className="col-sm-2 calign mb-2">
            <input type="button" className="bt-back" value="Back" onClick={goMain} />
          </div>
          <div className="col-sm-3">
          </div>

          <div className="col-sm-8 mb-4">
            <ReadmeFileContent
            title={currentReadme}
            content={readmeObject.find(e => e.id === currentReadme)}
            changePosition={changePosition}
            forRelanderng={forRelanderng}
            position={position}
            setPosition={setPosition}
            deleteContent={deleteContent}
            changeTextArea={changeTextArea}
            />
          </div>

          <div className="col-sm-4 mr-2 sideBanner">
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
        </div>

      </Wrapper>
  );
}

export default Editor;
