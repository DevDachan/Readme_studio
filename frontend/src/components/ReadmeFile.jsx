import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


function ReadmeFile(props) {
  const navigate = useNavigate();

  return (
    <li>
      <a href="./"> A.md</a>
    </li>
  );
}

export default ReadmeFile;
