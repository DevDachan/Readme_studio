import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import MDEditor from "@uiw/react-md-editor";
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

function ReadmeFileComponent(props) {
  const navigate = useNavigate();

  //for content variable
  const title = props.title;
  const content = props.curreadme.content;
  const type = props.curreadme.type;
  const position = props.position;

  // for contents action
  const setContent = props.setContent;
  const setPosition= props.setPosition;

  const changePosition = props.changePosition;
  const changeTextArea = props.changeTextArea;
  const changeLicense = props.changeLicense;
  const changeArchitecture = props.changeArchitecture;
  const changePeriod = props.changePeriod;

  const deleteContent = props.deleteContent;
  const pasteContent = props.pasteContent;
  const deleteReadme = props.deleteReadme;

  //for Header variable
  const readmeList= props.readmeList;
  const currentReadme=props.currentReadme;

  //for Header action
  const setCurrentReadme=props.setCurrentReadme;
  const addReadme=props.addReadme;
  const goResult=props.goResult;

  const previewChange = props.previewChange;
  const list = [""];


  //---------------------- Web API table function ------------------------------
  // change HTML table to markdown table
  function makeTableWebapi(data){
    var countTd = data.split('</tr>')[0].split('<td><p contenteditable="true">').length -1;
    var line = "|---";
    line = line.repeat(countTd);
    line = "\n"+line + "|\n";

    var temp = data.replace(/\n\s*/g, '<br>');
    temp = temp.replace(/<td><p contenteditable="true">\s*/g, '');
    temp = temp.replace(/<\/p><\/td>\s*/g, '|');
    temp = temp.replace(/<\/tr>\s*/, line);
    temp = temp.replace(/<tr>\s*/g, '|');
    temp = temp.replace(/<\/tr>\s*/g, "\n");
    temp = temp.replace(/<!-- -->\s*/g, "");
    temp = temp.replace(/<\/table>\s*/, "");

    temp = temp.split('class="webapi-table">')[1];
    return temp;
  }

  //****************************************************************************************
  // HTML table edit event
  const changeTableWebapi = (e) => {
    if(e.target.innerText === ""){
      e.target.innerText = " ";
    }
    e.target.innerText = e.target.innerText.replace(/\n/g, "<br>");

    var id = e.target.parentElement.parentElement.parentElement.id;
    var content = document.getElementById(id).outerHTML;
    content = content.replace(/&lt;\s*/g, "<");
    content = content.replace(/&gt;\s*/g, ">");
    content = makeTableWebapi(content);
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    tempReadme.find(e => e.id === currentReadme).content[id.split("table_")[1]] = "## Web API<br><!-- Web API -->\n" + content;
    setContent(tempReadme);
    e.target.innerText = e.target.innerText.replace(/<br>\s*/g, "\n");
  }

  //****************************************************************************************
  // change markdown table to HTML table
  function parseTableWebapi(data, id){
    data = data.replace(/&nbsp;\s*/g, " ");
    data = data.replace(/<br>\s*/g, "\n");
    var trTemp = data.split("|\n");
    const tempList = [""];
    const tr = [""];
    for(var i = 0; i < trTemp.length-1; i++){
      const td = [];
      var tdTemp = trTemp[i].split("|");
      if(i == 1) continue;
      for(var w = 1; w < tdTemp.length; w++){
        td.push(<td>
                  <p
                    contentEditable onBlur={(e) => changeTableWebapi(e)}>{tdTemp[w]}
                  </p>
                </td>);
      }
      tr.push( <tr> {td} </tr>);
    }
    tempList.push( <table id={"table_"+id} className="webapi-table"> {tr} </table>);
    return tempList;
  }

  //---------------------- DB table component function ------------------------------
  // HTML table -> Markdown Table
  function makeTableDb(data){
    var countTd = data.split('</tr>')[0].split('<td><p contenteditable="true">').length -1;
    var temp = data.replace(/\n\s*/g, '  <br>');
    temp = temp.replace(/<table class="db-table">\s*/g,"\n|*Column Name*|\n|-----|\n");
    temp = temp.replace(/<\/h3>\s*/g," \n");
    temp = temp.replace(/<h3>\s*/g,"#### ");
    temp = temp.replace(/<td><p contenteditable="true">\s*/g, '\n');
    temp = temp.replace(/<\/p><\/td>\s*/g, '|');
    temp = temp.replace(/<tr>\s*/g, '|');
    temp = temp.replace(/<\/tr>\s*/g, "\n");
    temp = temp.replace(/<!-- -->\s*/g, "");
    temp = temp.replace(/<\/table>\s*/g, "\n");
    return temp;
  }

  //****************************************************************************************
  // HTML table edit event
  const changeTableDb = (e) => {
    if(e.target.innerText === ""){
      e.target.innerText = " ";
    }
    e.target.innerText = e.target.innerText.replace(/\n/g, "<br>");

    var id = e.target.parentElement.parentElement.parentElement.parentElement.id;
    var content = document.getElementById(id).innerHTML;
    content = content.replace(/&lt;\s*/g, "<");
    content = content.replace(/&gt;\s*/g, ">");
    content = makeTableDb(content);
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    tempReadme.find(e => e.id === currentReadme).content[id.split("dbdiv_")[1]] = "<!-- DB Table -->\n" + content;
    setContent(tempReadme);
    e.target.innerText = e.target.innerText.replace(/<br>/g, "\n");
  }

  //****************************************************************************************
  // Markdown table -> HTML Table
  function parseTableDb(data, id){
    data = data.replace(/&nbsp;/g, " ");
    data = data.replace(/<br>/g, "\n");
    var trTemp = data.split("|\n");
    const tempList = [""];
    const tr = [""];
    for(var i = 1; i < trTemp.length-1; i++){
      const td = [];
      var tdTemp = trTemp[i].split("|");
      for(var w = 1; w < tdTemp.length; w++){
        td.push(<td>
                  <p contentEditable onBlur={(e) => changeTableDb(e)}> {tdTemp[w]} </p>
                </td>);
      }
      tr.push( <tr> {td} </tr>);
    }
    tempList.push( <table className="db-table"> {tr} </table>);
    return tempList;
  }


  //****************************************************************************************
  // Rendering step using parseTabledb Function
  function dbTable(data,id){
    const tempList = [""];
    var colTemp = data.split(/\|\*.*?\*\||####/g);
    var tableCount = (colTemp.length - 1) / 2;
    for(var i = 0; i < tableCount; i++){
      tempList.push(<h3>{colTemp[2*i+1].replace(/<br>/g, "")} </h3>);
      tempList.push(parseTableDb(colTemp[2*i+2],id + "_" + i));
    }
    return <div id={"dbdiv_"+id}> {tempList} </div>;
  }


  //------------------ Dependency ----------------------------------------
  function parseTableDependency(data, id){
    data = data.replace(/&nbsp;/g, " ");
    data = data.replace(/<br>/g, "\n");
    var trTemp = data.split("|\n");
    const tempList = [""];
    tempList.push( <div dangerouslySetInnerHTML={{__html:  data.split(/```/)[0]}} /> );
    return tempList;
  }


  //------------------------ Header component function  -----------------------------------
  const editHeaderText = (e) =>{
    var data = e.target.value;
    data = data.replace(/ /g, "_");
    var id = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    let modifiedHeader = tempReadme.find(e => e.id === currentReadme).content[id].replace(/text=[^&]*/, `text=${data}`);

    tempReadme.find(e => e.id === currentReadme).content[id] = modifiedHeader + "\n";
    setContent(tempReadme);
  }

  //****************************************************************************************
  const editHeaderSize = (e) =>{
    var data = e.target.value;
    var id = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    let modifiedHeader = tempReadme.find(e => e.id === currentReadme).content[id].replace(/fontSize=[^)]*/, `fontSize=${data}`);

    tempReadme.find(e => e.id === currentReadme).content[id] = modifiedHeader + "\n";
    setContent(tempReadme);
  }

  //****************************************************************************************
  const changeHaderType = (e) =>{
    var i = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    tempReadme.find(e => e.id === currentReadme).content[i] = tempReadme.find(e => e.id === currentReadme).content[i].replace(/type=[^&]*/,'type='+e.target.value);
    setContent(tempReadme);
  }

  //****************************************************************************************
  const changeHeaderColor = (e) =>{
    var i = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    var color = e.target.value.split("#")[1];
    tempReadme.find(e => e.id === currentReadme).content[i] = tempReadme.find(e => e.id === currentReadme).content[i].replace(/color=[^&]*/,'color='+color);
    setContent(tempReadme);
  }

  //****************************************************************************************
  const changeHeaderFontColor = (e) =>{
    var i = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    var color = e.target.value.split("#")[1];
    tempReadme.find(e => e.id === currentReadme).content[i] = tempReadme.find(e => e.id === currentReadme).content[i].replace(/fontColor=[^&]*/,'fontColor='+color);
    setContent(tempReadme);
  }


  // ----------------------------- change position function  --------------------------------------------------------
  const checkedPosition = (e) => {
    var prevPosition = position;
    setPosition(e.target.value);
    setTimeout(function() {
      document.getElementById("postionChangeDown"+(e.target.value-1)).focus();
    }, 1);
  }

  //****************************************************************************************
  const focusIn = (e) =>{
    var id = e.target.parentElement.parentElement.parentElement.parentElement.id;
    id = Number(id.split("_")[2])+1;
    setPosition(id);
  }


  //---------------------------- Make Component content  ----------------------------------------------
  for(var i = 0; i< content.length; i++){
    var curContent = "";
    var tempNum = i;
    if(type[i] == "Text" || type[i] == "Default Data"){
        curContent = <MDEditor
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
    }else if(type[i] == "License"){
          curContent = <MDEditor
            height={200}
            id={"md_editor_"+i}
            name={i}
            value={content[i].split("## License\n")[1]}
            onChange={ (e,v) => changeLicense(e,v)}
            onFocus={ (e) => focusIn(e)}
            color={"black"}
            key={"md_editor"+i}
            highlightEnable={false}
            />;
    }else if(type[i] == "All Data"){
      curContent = "<!-- All Data -->";

    }else if(type[i] == "DB Table"){
      curContent = content[i].split("<!-- DB Table -->\n")[1];
      curContent = dbTable(curContent,i);

    }else if(type[i] == "Dependency"){
      curContent = parseTableDependency(content[i].split("## Dependencies<br>")[1],i);

    }else if(type[i] == "WebAPI"){
      curContent = parseTableWebapi(content[i].split("<!-- Web API -->\n")[1],i);

    }else if(type[i] == "Contributor"){
      curContent = <div dangerouslySetInnerHTML = {{__html: content[i].split("## Contributor<br>")[1]}}>
        </div>;

    }else if(type[i] == "Social"){
      curContent = <div dangerouslySetInnerHTML = {{__html: content[i].split("## Social<br>")[1]}}>
        </div>;

    }else if(type[i] == "Architecture"){
      curContent = <MDEditor
        height={200}
        id={"md_editor_"+i}
        name={i}
        value={content[i].split("<!-- Project Architecture -->")[1]}
        onChange={ (e,v) => changeArchitecture(e,v)}
        onFocus={ (e) => focusIn(e)}
        color={"black"}
        key={"md_editor"+i}
        highlightEnable={false}
      />;

    }else if(type[i] == "Header"){
      var headerTag = "<img src=\"" + content[i].split("\(")[1].split("\)")[0] + "\" />";
      var headerText = content[i].split("&section=header&text=")[1].split("&")[0];
      var headerSize = content[i].split("fontSize=")[1].split("\)")[0];
      var headerType = content[i].split("?type=")[1].split("&")[0];
      var fontColor = content[i].split("fontColor=")[1].split("&")[0];
      var bgColor = content[i].split("color=")[1].split("&")[0];

      curContent = <div className="row">
        <div className="col-sm-4 mb-4">
          <select id={i} class="header-select" value={headerType} onChange={changeHaderType}>
            <option className="file-selector-item" value="Wave" > Wave </option>
            <option className="file-selector-item" value="Egg" > Egg </option>
            <option className="file-selector-item" value="Shark" > Shark </option>
            <option className="file-selector-item" value="Rect" > Rect </option>
            <option className="file-selector-item" value="Rounded" > Rounded </option>
            <option className="file-selector-item" value="Slice" > Slice </option>
            <option className="file-selector-item" value="Waving" > Waving </option>
            <option className="file-selector-item" value="Cylinder" > Cylinder </option>
          </select>
        </div>
        <div className="col-sm-3">
          <h3 className="ralign"> Font Color </h3>
        </div>
        <div className="col-sm-1">
          <input type="color" id={i} className="ip-header-color" defaultValue={"#"+fontColor} onChange={changeHeaderFontColor}/>
        </div>
        <div className="col-sm-2">
          <h3 className="ralign"> Color </h3>
        </div>
        <div className="col-sm-2">
          <input type="color" id={i} className="ip-header-color" defaultValue={"#"+bgColor} onChange={changeHeaderColor}/>
        </div>


        <div className="col-sm-2">
          <h3> Text </h3>
        </div>
        <div className="col-sm-10">
          <input type="text" defaultValue="hi" id={i} className="ip-header" value={headerText} onChange={editHeaderText} autoComplete="off" />
        </div>
        <div className="col-sm-2">
          <h3> Size </h3>
        </div>
        <div className="col-sm-10">
          <input type="text" defaultValue="hi" id={i} className="ip-header" value={headerSize} onChange={editHeaderSize} autoComplete="off" />
        </div>

        <div className="calign" dangerouslySetInnerHTML = {{__html:  headerTag }}></div>
      </div>;

    }else if(type[i] == "Period"){
      curContent = <div>
        <div className="dateBox" >Start date : <input type="date" data-placeholder="날짜 선택" id={"period_start" + i} onChange={changePeriod} name={i}></input></div>
        <div className="brCSS"></div>
        <div className="dateBox" >End date : <input type="date" data-placeholder="날짜 선택" id={"period_end" + i} name={i} onChange={changePeriod}></input></div>
      </div>;

    }else{
      curContent = content[i];
    }

    if( Number(i) === Number(position)-1){
      list.push(
        <div>
          <div className="readme" id={"readme_"+i} key={i}>
            <div className="readme-header row">
              <div className="col-sm-12">
                <button className="bt-up" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}} />
                <button className="bt-down" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition} />
              </div>
              <div className="col-sm-12">
                <h3 className="component-title"> {type[i]}</h3>
              </div>
            </div>

            <div className="readme-content">
              <div className="readme-content-detail">{curContent}</div>
            </div>
            <div className="readme-footer row">
              <div className="col-sm-12 border-line">
              </div>
              <div className="col-sm-8">
              </div>
              <div className="col-sm-2 ralign">
                <button className="paste-readmeComponent" onClick={pasteContent} key={"paste_"+i} value={i} / >
              </div>
              <div className="col-sm-2 lalign">
                <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i} / >
              </div>
            </div>

          </div>
          <div className="calign">
            <button className="bt-changePosition-checked" id={"select_"+i} id={"select_"+i} onClick={checkedPosition} key={"input_"+i} value={i+1}> Selected </button>
          </div>
        </div>);
    }else{
      list.push(
        <div>
          <div className="readme" id={"readme_"+i} key={i}>
            <div className="readme-header row">
              <div className="col-sm-12">
                <button className="bt-up" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}} />
                <button className="bt-down" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition} />
              </div>
              <div className="col-sm-12">
                <h3 className="component-title"> {type[i]}</h3>
              </div>
            </div>
            <div className="readme-content">
              <div className="readme-content-detail">{curContent}</div>
            </div>
            <div className="readme-footer row">
              <div className="col-sm-12 border-line">
              </div>

              <div className="col-sm-8">
              </div>
              <div className="col-sm-2 ralign">
                <button className="paste-readmeComponent" onClick={pasteContent} id={i} key={"paste_"+i} value={i} / >
              </div>
              <div className="col-sm-2 lalign">
                <button className="delete-readmeComponent" onClick={deleteContent} key={"delete_"+i} value={i} / >
              </div>
            </div>
          </div>
          <div className="calign">
            <button className="bt-changePosition" id={"select_"+i} onClick={checkedPosition} key={"input_"+i} value={i+1}> + </button>
          </div>
        </div>);
    }
  }

  return (
    <Wrapper>
      <div className="contentDiv mb-2">
        <div className="row border-line" style={{height: "6rem"}}>

          <div className="col-sm-1 calign mb-3 div-add">
            <input type="button" className="bt-add" value="" onClick={addReadme} />
          </div>

          <div className="col-sm-4 mb-2">
            <br/>
            <ReadmeFileSelect readmeList={readmeList} currentReadme={currentReadme} setCurrentReadme={setCurrentReadme}/>
          </div>

          <div className="col-sm-7 ralign mb-3">
            <input type="button" className="bt-generate ralign" value="👉🏻 Generate MD Files " onClick={goResult} />
          </div>
        </div>

        <div className="row div-component-header">
          <div className="col-sm-12">
            <h3 className="header-text"> {title} </h3>
          </div>

          <div className="col-sm-12 calign">
              <button className=" bt-preview" onClick={previewChange} variant="outline-primary">Preview</button>
              <button className="bt-deleteReadme"  onClick={deleteReadme}>DELETE README</button>

          </div>
        </div>
        {list}
      </div>
    </Wrapper>
  );
}

export default ReadmeFileComponent;
