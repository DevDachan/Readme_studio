import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;


function ReadmeFileContent(props) {
  const navigate = useNavigate();

  const item = props.currentReadme;
  const setItem = props.setCurrentReadme;


  const selectList = e =>{
    setItem(e.target.value);
  }

  return (
      <Wrapper>
        <select id="file-selector" value={item} onChange={selectList}>
          <option className="file-selector-item" value="A" > A </option>
          <option className="file-selector-item" value="B" > B </option>
          <option className="file-selector-item" value="C" > C </option>
          <option className="file-selector-item" value="D" > D </option>
        </select>
      </Wrapper>
  );
}

export default ReadmeFileContent;
