import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

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
  const content = props.content.content;

  let temp = "";

  for(var i = 0; i< content.length; i++){
    temp = temp +"\n" + content[i];
  }

  return (
      <Wrapper>
        <div className="readmeDiv">
          <Markdown
        		style={{
              padding: "30px",
              backgroundColor: "white",
              borderRadius: "20px",
              color: "black"
            }}
            source={temp}
          />
        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
