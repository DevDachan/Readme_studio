import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: 100%;
    flex-direction: column;
    justify-content: center;
`;


function ReadmeFileContent(props) {
  const navigate = useNavigate();
  const content = props.content;
  const list = [];


  for(var i = 0; i< content.length; i++){
    list.push(<div className="readme">{content[i]}</div>);
  }

  return (
      <Wrapper>
        <div className="contentDiv mb-2">
          <h3> A.md </h3>
          {list}

        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
