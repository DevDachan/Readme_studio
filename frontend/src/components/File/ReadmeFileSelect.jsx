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


function ReadmeFileSelect(props) {
  const navigate = useNavigate();

  const item = props.currentReadme;
  const setItem = props.setCurrentReadme;
  const readmeList = props.readmeList;

  const selectList = e =>{
    setItem(e.target.value);
  }

  return (
    <Wrapper>
      <select id="file-selector" value={item} onChange={selectList}>
        {
          readmeList.map((it) => (
            <option className="file-selector-item" key={it.id} value={it.id} > {it.id} </option>
          ))
        }
      </select>
    </Wrapper>
  );
}

export default ReadmeFileSelect;
