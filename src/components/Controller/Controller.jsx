import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
`;


function ReadmeFileContent(props) {
  const navigate = useNavigate();

  return (
      <Wrapper>
        <div className="controller-div row">
          <h3 className="controoler-title"> Controller</h3>
          <input type="button" className="mb-2" value="Template" />
          <input type="button" className="mb-2" value="Contributor" />
        </div>
      </Wrapper>
  );
}

export default ReadmeFileContent;
