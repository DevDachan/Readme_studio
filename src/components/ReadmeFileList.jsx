import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import ReadmeFile from "./ReadmeFile";

function ReadmeFileList(props) {
  const navigate = useNavigate();

  const readmeFileList = useState();

  const Wrapper = styled.div`
      padding: 0 2.5em;
      margin: 0 auto;
      width: calc(100% - 32px);
      display: flex;
      flex-direction: column;
      justify-content: center;
  `;

  return (
      <Wrapper>
        <ul class="readme">
          <ReadmeFile />
        </ul>

      </Wrapper>
  );
}

export default ReadmeFileList;
