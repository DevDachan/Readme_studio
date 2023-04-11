import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import Md_editor from "@uiw/react-md-editor";



const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: 100%;
    flex-direction: column;
    justify-content: center;
`;

function ReadmeFileContent(props) {
  const navigate = useNavigate();
  const position = props.position;
  const setPosition= props.setPosition;
  const deleteReadme = props.deleteReadme;
  const changePosition = props.changePosition;
  const content = props.content.content;
  const changeTextArea = props.changeTextArea;
  const changePeriod = props.changePeriod;
  const deleteContent = props.deleteContent;
  const handleOpen = props.handleOpen;
  const title = props.title;
  const list = [""];

  const checkedPosition = (e) => {
    setPosition(e.target.value);
  }

  //initial make content List

  for(var i = 0; i< content.length; i++){
    var cur_content = "";
    var temp_num = i;
    if(content[i].includes("empty_textarea")){
        cur_content = <Md_editor
          height={200}
          id={"md_editor_"+i}
          name={i}
          value={content[i].split("<!-- empty_textarea -->\n")[1]}
          onChange={ (e,v) => changeTextArea(e,v)}
          color={"black"}
          key={"md_editor"+i}
          highlightEnable={false}
          />;
    }else if(content[i].includes("<!-- All Data -->")){
      cur_content = "<!-- All Data -->";
    }else if(content[i].includes("<!-- Web API -->")){
      cur_content = <Md_editor
        height={200}
        id={"md_editor_"+i}
        name={i}
        value={content[i].split("<!-- Web API -->\n")[1]}
        onChange={ (e,v) => changeTextArea(e,v)}
        color={"black"}
        key={"md_editor"+i}
        highlightEnable={false}
        />;
    }else if(content[i].includes("https://ifh.cc")){
      cur_content = <div>
        <div className="dateBox" >Start date : <input type="date" data-placeholder="날짜 선택" id={"period_start" + i} onChange={changePeriod} name={i}></input></div>
        <div className="brCSS"></div>
        <div className="dateBox" >End date : <input type="date" data-placeholder="날짜 선택" id={"period_end" + i} name={i} onChange={changePeriod}></input></div>
      </div>;
    }else{
      cur_content = content[i];
    }


    if( Number(i) === Number(position)-1){
      list.push(
        <div>
        <div className="readme" key={i}>
          <div className="row mb-3">
            <div className="col-sm-8">
              <button className="bt-updown" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}}>
                <img className="img-updown" src="../images/up_arrow.png" style={{width:"30%"}}/ >
              </button>
              <button className="bt-updown" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition}>
                <img className="img-updown" src="../images/down_arrow.png" style={{width:"30%"}}/ >
              </button>
            </div>
            <div className="col-sm-4"  style={{"textAlign":"right"}}>
              <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i}> Delete </button>
            </div>
          </div>
          <p>{cur_content}</p>
          </div>
          <div className="div-readmeComponent">
            <button className="input-readmeComponent-checked" onClick={checkedPosition} key={"input_"+i} value={i+1}> Selected </button>
          </div>
        </div>);
    }else{
      list.push(
        <div>
          <div className="readme" key={i}>
            <div className="row mb-3">
              <div className="col-sm-8">
                <button className="bt-updown" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}}>
                  <img className="img-updown" src="../images/up_arrow.png" style={{width:"30%"}} />
                </button>
                <button className="bt-updown" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition}>
                  <img className="img-updown" src="../images/down_arrow.png" style={{width:"30%"}}/ >
                </button>
              </div>
              <div className="col-sm-4" style={{"textAlign":"right"}}>
                <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i}> Delete </button>
              </div>
            </div>
            <p>{cur_content}</p>
          </div>
          <div className="div-readmeComponent">
            <button className="input-readmeComponent" onClick={checkedPosition} key={"input_"+i} value={i+1}> + </button>
          </div>
        </div>);
    }
  }

  return (
      <Wrapper>
        <div className="contentDiv mb-2">
          <div className="row div-component-header">
            <div className="col-sm-9">
              <h3> {title} </h3>
            </div>
            <div className="col-sm-3">
              <button className="bt-preview" onClick={handleOpen} variant="outline-primary">Preview</button>
            </div>
          </div>
          {list}
          <div className="col-sm-12 calign">
            <input type="button" className="bt-back mt-5" value="Delete This README"  onClick={deleteReadme}/>
          </div>
        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
