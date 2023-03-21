import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import { unified } from "unified";
import markdown from "remark-parse";
import remark2rehype from "remark-rehype";
import html from "rehype-stringify";
import MDEditor from '@uiw/react-md-editor';

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: 100%;
    flex-direction: column;
    justify-content: center;
`;


function ReadmeFileContent(props) {
  const navigate = useNavigate();
  const content = props.content.content;
  const title = props.title;
  const list = [""];
  let temp = "";

  for(var i = 0; i< content.length; i++){
    var template_md = unified()
        .use(markdown)
        .use(remark2rehype)
        .use(html)
        .processSync(content[i]).toString();

    temp = temp + content[i];

    list.push(<div className="readme" key={i}> {content[i]}</div>);
    //list.push(<div className="readme" key={"md_"+i} dangerouslySetInnerHTML={ {__html: template_md}}></div>);
  }

  return (
      <Wrapper>
        <div className="contentDiv mb-2">
          <h3> {title} </h3>
          {list}
        </div>
        <MDEditor.Markdown
      		style={{
            padding: "30px",
            backgroundColor: "#c7c7c7",
            borderRadius: "20px"
          }}
      		source={temp}
        />
      </Wrapper>
  );
}

export default ReadmeFileContent;
