import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import Md_editor from "@uiw/react-md-editor";
import ReadmeFileSelect from "./ReadmeFileSelect";
import Markdown from "@uiw/react-markdown-preview";
import ReactDOMServer from 'react-dom/server';

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: 100%;
    flex-direction: column;
    justify-content: center;
`;


function parseTable(data){
  var tr_temp = data.split("|\n");
  const list = [""];
  const tr = [""];

  for(var i = 0; i < tr_temp.length-1; i++){
    const td = [];
    var td_temp = tr_temp[i].split("|");
    if(i == 1) continue;
    for(var w = 1; w < td_temp.length; w++){
      td.push(<td> {td_temp[w]}  </td>);
    }
    tr.push( <tr> {td} </tr>);
  }

  list.push( <table className="webapi-table" contenteditable="true"> {tr} </table>);

  return list;
}

function makeTable(data){
  var count_td = data.split('</tr>')[0].split('<td>').length -1;
  var line = "|---";
  line = line.repeat(count_td);
  line = "\n"+line + "|\n";
  var temp = data.replace(/<table class="webapi-table">/g, '');
  temp = temp.replace(/<\/table>/g, '');
  temp = temp.replace(/<td>\s*/g, '');
  temp = temp.replace(/<\/td>/g, '|');
  temp = temp.replace(/<\/tr>/, line);
  temp = temp.replace(/<tr>/g, '|');
  temp = temp.replace(/<\/tr>/g, "\n");
  temp = temp.replace(/<!-- -->/g, "");
  return temp;
}



function ReadmeFileComponent(props) {
  const navigate = useNavigate();
  //for Header
  const readmeList= props.readmeList;
  const setCurrentReadme=props.setCurrentReadme;
  const currentReadme=props.currentReadme;
  const addReadme=props.addReadme;
  const generateReadme=props.generateReadme;


  // for content
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

  const focusIn = (e) =>{
    var id = e.target.parentElement.parentElement.parentElement.parentElement.id;
    id = Number(id.split("_")[2])+1;
    setPosition(id);
  }
  //initial make content List


  const changeWebApi = (e) =>{
    const inputString = e.outerHTML;
    // 이때 inputString은 전체 Dom객체를 나타낸다.
  }


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
          onFocus={ (e) => focusIn(e)}
          color={"black"}
          key={"md_editor"+i}
          highlightEnable={false}
          />;
    }else if(content[i].includes("<!-- All Data -->")){
      cur_content = "<!-- All Data -->";
    }else if(content[i].includes("<!-- DB Table -->")){
      cur_content = parseTable(content[i].split("<!-- DB Table -->\n")[1]);
    }else if(content[i].includes("<!-- Web API -->")){
      /*cur_content = <Md_editor
        height={200}
        id={"md_editor_"+i}
        name={i}
        value={content[i].split("<!-- Web API -->\n")[1]}
        onChange={ (e,v) => changeTextArea(e,v)}
        onFocus={ (e) => focusIn(e)}
        color={"black"}
        key={"md_editor"+i}
        highlightEnable={false}
        contentEditable
        suppressContentEditableWarning
        />;*/
      cur_content = parseTable(content[i].split("<!-- Web API -->\n")[1]);

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
        <div className="readme" id={"readme_"+i} key={i}>
          <div className="readme-header row">
            <div className="col-sm-8">
              <button className="bt-up" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}} />
              <button className="bt-down" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition} />
            </div>
            <div className="col-sm-4"  style={{"textAlign":"right"}}>
              <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i}> X </button>
            </div>


          </div>
          <div className="readme-footer">
            <p className="readme-footer-content">{cur_content}</p>
          </div>
          </div>
          <div className="div-readmeComponent">
            <button className="input-readmeComponent-checked" id={"select_"+i} id={"select_"+i} onClick={checkedPosition} key={"input_"+i} value={i+1}> Selected </button>
          </div>
        </div>);
    }else{
      list.push(
        <div>
          <div className="readme" id={"readme_"+i} key={i}>
            <div className="readme-header row">
              <div className="col-sm-8">
                <button className="bt-up" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}} />
                <button className="bt-down" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition} />
              </div>
              <div className="col-sm-4" style={{"textAlign":"right"}}>
                <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i}> X </button>
              </div>
            </div>
            <div className="readme-footer">
              <p className="readme-footer-content">{cur_content}</p>
            </div>
          </div>
          <div className="div-readmeComponent">
            <button className="input-readmeComponent" id={"select_"+i} onClick={checkedPosition} key={"input_"+i} value={i+1}> + </button>
          </div>
        </div>);
    }
  }

  return (
      <Wrapper>
        <div className="contentDiv mb-2">
          <div className="row border-line" style={{height: "6rem"}}>
            <div className="col-sm-1 calign mb-3">
              <input type="button" className="bt-add" value="" onClick={addReadme} />
            </div>
            <div className="col-sm-4 mb-2">
              <br/>
              <ReadmeFileSelect readmeList={readmeList} currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
            </div>

            <div className="col-sm-7 ralign mb-3">
              <input type="button" className="bt-generate ralign" value="👉🏻 Generate MD Files " onClick={generateReadme} />
            </div>

          </div>

          <div className="row div-component-header">
            <div className="col-sm-12">
              <h3 className="header-text"> {title} </h3>
            </div>
            <div className="col-sm-12 calign">
                <button className=" bt-preview" onClick={handleOpen} variant="outline-primary">Preview</button>
                <button className="bt-deleteReadme"  onClick={deleteReadme}>DELETE README</button>

            </div>
          </div>
          {list}
        </div>
      </Wrapper>
  );
}

export default ReadmeFileComponent;
