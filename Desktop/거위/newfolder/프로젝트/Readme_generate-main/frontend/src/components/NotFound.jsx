import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 8em;
`;

function NotFound(props) {
    const navigate = useNavigate();
    const onClick = () =>{
      navigate(`/`);
    }


    return (
        <Wrapper>
          <div style={{margin: "30px"}}>
            <h1>잘못된 접근입니다.</h1>
          </div>
          <button onClick={onClick}> 메인 화면</button>
        </Wrapper>
    );
}

export default NotFound;
