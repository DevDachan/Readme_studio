import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import { unified } from "unified";
import markdown from "remark-parse";
import remark2rehype from "remark-rehype";
import html from "rehype-stringify";
import Markdown from "@uiw/react-markdown-preview";

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

  const changePosition = props.changePosition;
  const content = props.content.content;
  const changeTextArea = props.changeTextArea;
  const changeEndPeriod = props.changeEndPeriod;
  const deleteContent = props.deleteContent;

  const title = props.title;
  const list = [""];
  let temp = "";

  const checkedPosition = (e) => {
    setPosition(e.target.value);
  }

  //initial make content List

  for(var i = 0; i< content.length; i++){
    var cur_content = "";
    
    if(content[i].includes("empty_textarea")){
      cur_content =  <textarea
          placeholder="여기에 입력하세요"
          value={content[i].split("<!-- empty_textarea -->\n")[1]}
          id={"textarea_"+i}
          name={i}
          onChange={changeTextArea}
          wrap="hard"
        ></textarea>;

    } else if(content[i].includes("https://ifh.cc") || content[i].includes("PeriodImage")){
      cur_content = <>
        <div className="dateBox" >Start date : <input type="date" data-placeholder="날짜 선택" id={"period_start" + i} name={i}></input></div>
        <div className="brCSS"></div>
        <div className="dateBox" >End date : <input type="date" data-placeholder="날짜 선택" id={"period_end" + i} name={i} onChange={changeEndPeriod}></input></div>
      </>;
    } else{
      cur_content = content[i];
    }

    var template_md = unified()
        .use(markdown)
        .use(remark2rehype)
        .use(html)
        .processSync(content[i]).toString();

    temp = temp +"\n" + content[i];

    if( Number(i) === Number(position)-1){
      list.push(
        <div className="readme" key={i}>
          <div className="row mb-3">
            <div className="col-sm-8">
              <select id="postionChange" value="change position" key={i} name={i} onChange={changePosition}>
              <option value="change position" disabled hidden>change position</option>
              {
                content.map((it, index) => (
                  <option className="file-selector-item" key={index+1} value={index+1} > {index+1} </option>
                ))
              }
              </select>
            </div>
            <div className="col-sm-4">
              <button className="delete-readmeContent" onClick={deleteContent} key={"delete_"+i} value={i}> Delete </button>
            </div>
          </div>
          <p>{cur_content}</p>
          <div className="div-readmeContent">
            <button className="input-readmeContent-checked" onClick={checkedPosition} key={"input_"+i} value={i+1}> V </button>
          </div>
        </div>);
    }else{
      list.push(
        <div className="readme" key={i}>
          <div className="row mb-3">
            <div className="col-sm-8">
              <select id="postionChange" value="change position" key={i} name={i} onChange={changePosition}>
              <option value="change position" disabled hidden>change position</option>
              {
                content.map((it, index) => (
                  <option className="file-selector-item" key={index+1} value={index+1} > {index+1} </option>
                ))
              }
              </select>
            </div>
            <div className="col-sm-4">
              <button className="delete-readmeContent" onClick={deleteContent} key={"delete_"+i} value={i}> Delete </button>
            </div>
          </div>
          <p>{cur_content}</p>
          <div className="div-readmeContent">
            <button className="input-readmeContent" onClick={checkedPosition} key={"input_"+i} value={i+1}> + </button>
          </div>
        </div>);
    }
  }

  return (
      <Wrapper>
        <div className="contentDiv mb-2">
          <h3> {title} </h3>
          {list}
        </div>
        <div className="readmeDiv">
          <Markdown
        		style={{
              padding: "30px",
              backgroundColor: "white",
              borderRadius: "20px",
              whiteSpace: 'pre-wrap',
              color: "black !important"
            }}
            source={temp}
          />
        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
