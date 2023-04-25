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


function ReadmeFileComponent(props) {
  const navigate = useNavigate();
  //for Header
  const readmeList= props.readmeList;
  const setCurrentReadme=props.setCurrentReadme;
  const currentReadme=props.currentReadme;
  const addReadme=props.addReadme;
  const generateReadme=props.generateReadme;
  const setContent = props.setContent;

  // for content
  const position = props.position;
  const setPosition= props.setPosition;
  const deleteReadme = props.deleteReadme;
  const changePosition = props.changePosition;
  const content = props.curreadme.content;
  const type = props.curreadme.type;
  const changeTextArea = props.changeTextArea;
  const changeLicense = props.changeLicense;
  const changePeriod = props.changePeriod;
  const deleteContent = props.deleteContent;
  const pasteContent = props.pasteContent;
  const handleOpen = props.handleOpen;
  const title = props.title;
  const list = [""];

  //---------------------- Web API table Editor ------------------------------
  // change HTML table to markdown table
  function makeTable_webapi(data){
    var count_td = data.split('</tr>')[0].split('<td><p contenteditable="true">').length -1;
    var line = "|---";
    line = line.repeat(count_td);
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

  // HTML table edit event
  const changeTable_webapi = (e) => {
    if(e.target.innerText === ""){
      e.target.innerText = " ";
    }
    e.target.innerText = e.target.innerText.replace(/\n/g, "<br>");

    var id = e.target.parentElement.parentElement.parentElement.id;
    var content = document.getElementById(id).outerHTML;
    content = content.replace(/&lt;\s*/g, "<");
    content = content.replace(/&gt;\s*/g, ">");
    content = makeTable_webapi(content);
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    tempReadme.find(e => e.id === currentReadme).content[id.split("table_")[1]] = "## Web API<br><!-- Web API -->\n" + content;
    setContent(tempReadme);
    e.target.innerText = e.target.innerText.replace(/<br>\s*/g, "\n");
  }


  // change markdown table to HTML table
  function parseTable_webapi(data, id){
    data = data.replace(/&nbsp;\s*/g, " ");
    data = data.replace(/<br>\s*/g, "\n");
    var tr_temp = data.split("|\n");
    const temp_list = [""];
    const tr = [""];
    for(var i = 0; i < tr_temp.length-1; i++){
      const td = [];
      var td_temp = tr_temp[i].split("|");
      if(i == 1) continue;
      for(var w = 1; w < td_temp.length; w++){
        td.push(<td>
                  <p
                    contentEditable onBlur={(e) => changeTable_webapi(e)}>{td_temp[w]}
                  </p>
                </td>);
      }
      tr.push( <tr> {td} </tr>);
    }
    temp_list.push( <table id={"table_"+id} className="webapi-table"> {tr} </table>);
    return temp_list;
  }

  //---------------------- DB table Editor ------------------------------
  function makeTable_db(data){
    var count_td = data.split('</tr>')[0].split('<td><p contenteditable="true">').length -1;

    var temp = data.replace(/\n\s*/g, '  <br>');
    temp = temp.replace(/<table class="db-table">\s*/g,"\n|*Column Name*|\n|-----|\n");
    temp = temp.replace(/<\/h3>\s*/g," \n");
    temp = temp.replace(/<h3>\s*/g,"### ");
    temp = temp.replace(/<td><p contenteditable="true">\s*/g, '\n');
    temp = temp.replace(/<\/p><\/td>\s*/g, '|');
    temp = temp.replace(/<tr>\s*/g, '|');
    temp = temp.replace(/<\/tr>\s*/g, "\n");
    temp = temp.replace(/<!-- -->\s*/g, "");
    temp = temp.replace(/<\/table>\s*/g, "\n");
    return temp;
  }

  // HTML table edit event
  const changeTable_db = (e) => {
    if(e.target.innerText === ""){
      e.target.innerText = " ";
    }
    e.target.innerText = e.target.innerText.replace(/\n/g, "<br>");

    var id = e.target.parentElement.parentElement.parentElement.parentElement.id;
    var content = document.getElementById(id).innerHTML;
    content = content.replace(/&lt;\s*/g, "<");
    content = content.replace(/&gt;\s*/g, ">");
    content = makeTable_db(content);
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    tempReadme.find(e => e.id === currentReadme).content[id.split("dbdiv_")[1]] = "<!-- DB Table -->\n" + content;
    setContent(tempReadme);
    e.target.innerText = e.target.innerText.replace(/<br>/g, "\n");
  }

  // change markdown table to HTML table
  function parseTable_db(data, id){
    data = data.replace(/&nbsp;/g, " ");
    data = data.replace(/<br>/g, "\n");
    var tr_temp = data.split("|\n");
    const temp_list = [""];
    const tr = [""];
    for(var i = 1; i < tr_temp.length-1; i++){
      const td = [];
      var td_temp = tr_temp[i].split("|");
      for(var w = 1; w < td_temp.length; w++){
        td.push(<td>
                  <p
                    contentEditable onBlur={(e) => changeTable_db(e)}>{td_temp[w]}
                  </p>
                </td>);
      }
      tr.push( <tr> {td} </tr>);
    }
    temp_list.push( <table className="db-table"> {tr} </table>);
    return temp_list;
  }

  function db_table(data,id){
    const temp_list = [""];
    var col_temp = data.split(/\|\*.*?\*\||###/g);
    var table_count = (col_temp.length - 1) / 2;
    for(var i = 0; i < table_count; i++){
      temp_list.push(<h3>{col_temp[2*i+1].replace(/<br>/g, "")} </h3>);
      temp_list.push(parseTable_db(col_temp[2*i+2],id + "_" + i));
    }
    return <div id={"dbdiv_"+id}> {temp_list} </div>;
  }

  const editHeaderText = (e) =>{
    var data = e.target.value;
    var id = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    let modifiedHeader = tempReadme.find(e => e.id === currentReadme).content[id].replace(/text=[^&]*/, `text=${data}`);

    tempReadme.find(e => e.id === currentReadme).content[id] = modifiedHeader;
    setContent(tempReadme);
  }

  const editHeaderSize = (e) =>{
    var data = e.target.value;
    var id = e.target.id;
    var tempReadme = JSON.parse(JSON.stringify(readmeList));
    let modifiedHeader = tempReadme.find(e => e.id === currentReadme).content[id].replace(/fontSize=[^)]*/, `fontSize=${data}`);

    tempReadme.find(e => e.id === currentReadme).content[id] = modifiedHeader;
    setContent(tempReadme);
  }


  const checkedPosition = (e) => {
    setPosition(e.target.value);
  }

  const focusIn = (e) =>{
    var id = e.target.parentElement.parentElement.parentElement.parentElement.id;
    id = Number(id.split("_")[2])+1;
    setPosition(id);
  }

  for(var i = 0; i< content.length; i++){
    var cur_content = "";
    var temp_num = i;
    if(type[i] == "Text" || type[i] == "Default Data"){
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
    }else if(type[i] == "License"){
          cur_content = <Md_editor
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
      cur_content = "<!-- All Data -->";
    }else if(type[i] == "DB Table"){
      cur_content = content[i].split("<!-- DB Table -->\n")[1];

      cur_content = db_table(cur_content,i);
    }else if(type[i] == "WebAPI"){
      cur_content = parseTable_webapi(content[i].split("<!-- Web API -->\n")[1],i);

    }else if(type[i] == "Contributor"){
      cur_content = <div dangerouslySetInnerHTML = {{__html: content[i].split("## Contributor<br>")[1]}}>
        </div>;
    }else if(type[i] == "Social"){
      cur_content = <div dangerouslySetInnerHTML = {{__html: content[i].split("## Social<br>")[1]}}>
        </div>;
    }else if(type[i] == "Header"){
      var header_tag = "<img src=\"" + content[i].split("\(")[1].split("\)")[0] + "\" />";
      var header_text = content[i].split("&section=header&text=")[1].split("&")[0];
      var header_size = content[i].split("fontSize=")[1].split("\)")[0];

      cur_content = <div className="row">
        <div className="col-sm-3">
          <h3> Text </h3>
        </div>
        <div className="col-sm-9">
          <input type="text" defaultValue="hi" id={i} className="ip-header" value={header_text} onChange={editHeaderText} autoComplete="off" />
        </div>
        <div className="col-sm-3">
          <h3> Size </h3>
        </div>
        <div className="col-sm-9">
          <input type="text" defaultValue="hi" id={i} className="ip-header" value={header_size} onChange={editHeaderSize} autoComplete="off" />
        </div>
        <div className="calign" dangerouslySetInnerHTML = {{__html:  header_tag }}></div>
      </div>;

      console.log(cur_content);


    }else if(type[i] == "Period"){
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
              <div className="col-sm-12">
                <button className="bt-up" id={"postionChangeUp"+i} key={"up"+i} name={i} onClick={changePosition} style={{marginRight:"5px"}} />
                <button className="bt-down" id={"postionChangeDown"+i} key={"down"+i} name={i} onClick={changePosition} />
              </div>
              <div className="col-sm-12">
                <h3 className="component-title"> {type[i]}</h3>
              </div>
            </div>

            <div className="readme-content">
              <div className="readme-content-detail">{cur_content}</div>
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
          <div className="div-readmeComponent">
            <button className="input-readmeComponent-checked" id={"select_"+i} id={"select_"+i} onClick={checkedPosition} key={"input_"+i} value={i+1}> Selected </button>
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
              <div className="readme-content-detail">{cur_content}</div>
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
