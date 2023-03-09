import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import ReadmeFile from "./ReadmeFile";

function ReadmeFileContent(props) {
  const navigate = useNavigate();

  const Wrapper = styled.div`
      padding: 0;
      margin: 0 auto;
      width: calc(100% - 32px);
      display: flex;
      flex-direction: column;
      justify-content: center;
  `;

  return (
      <Wrapper>
        <ul className="readme">
          <ReadmeFile />
          <ReadmeFile />
          <ReadmeFile />
          <ReadmeFile />
        </ul>
      </Wrapper>
  );
}

export default ReadmeFileContent;
