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


function ReadmeFileResultContent(props) {
  const navigate = useNavigate();

  const item = props.currentReadme;
  const setItem = props.setCurrentReadme;
  const readmeList = props.readmeList;

  const selectList = e =>{
    setItem(e.target.value);
  }

  return (
      <Wrapper>
        <ul className="file-list">
          <li className="file-list-header"> README List </li>
          {
            readmeList.map((it) => (
              <li className="file-list-item" value={it.id}> {it.id}</li>
            ))
          }

        </ul>
      </Wrapper>
  );
}

export default ReadmeFileResultContent;
