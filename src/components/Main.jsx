import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


function Main(props) {
  const navigate = useNavigate();
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
        <header id="header">
          <h1>Eventually</h1>
          <p>A simple template for telling the world when you'll launch<br />
          your next big thing. Brought to you by <a href="http://html5up.net">HTML5 UP</a>.</p>
        </header>


        <form id="signup-form" method="post" action="#">
          <input type="email" name="email" id="email" placeholder="Email Address" />
          <input type="submit" value="Sign Up" />
        </form>
      </Wrapper>
  );
}

export default Main;
