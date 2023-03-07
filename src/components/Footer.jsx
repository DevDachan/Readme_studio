import React, { useState } from "react";
import styled from "styled-components";


function Footer(props) {

  const Wrapper = styled.div`
      padding: 0 2.5em;
      margin: 0 auto;
      width: calc(100% - 32px);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
  `;

  return (
      <Wrapper>
        <ul className="icons">
          <li><a href="#" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
          <li><a href="#" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
          <li><a href="#" class="icon brands fa-github"><span class="label">GitHub</span></a></li>
          <li><a href="#" class="icon fa-envelope"><span class="label">Email</span></a></li>
        </ul>
        <ul className="copyright">
          <li>&copy; Untitled.</li><li>Credits: <a href="http://html5up.net">HTML5 UP</a></li>
        </ul>
      </Wrapper>
  );
}

export default Footer;
