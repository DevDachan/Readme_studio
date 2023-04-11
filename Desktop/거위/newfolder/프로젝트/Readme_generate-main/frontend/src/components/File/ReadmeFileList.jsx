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
        <ul className="file-list">
          <li className="file-list-header"> README List </li>
          <li className="file-list-item"  value="A" > A </li>
          <li className="file-list-item"  value="B" > B </li>
          <li className="file-list-item"  value="C" > C </li>
          <li className="file-list-item"  value="D" > D </li>
        </ul>
      </Wrapper>
  );
}

export default ReadmeFileContent;
