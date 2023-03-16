import React, { useState } from "react";
import styled from "styled-components";


const Wrapper = styled.div`
    padding: 0 2.5em;
    margin: 0 auto;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    justify-content: center;
`;

function Footer(props) {
  return (
      <Wrapper>
        <ul className="icons">
          <li><a href="#" className="icon brands fa-twitter"><span className="label">Twitter</span></a></li>
          <li><a href="#" className="icon brands fa-instagram"><span className="label">Instagram</span></a></li>
          <li><a href="#" className="icon brands fa-github"><span className="label">GitHub</span></a></li>
          <li><a href="#" className="icon fa-envelope"><span className="label">Email</span></a></li>
        </ul>
        <ul className="copyright">
          <li>&copy; ReadmeGenerate.</li><li>Copyright: <a href="http://html5up.net">HTML5 UP</a></li>
        </ul>
      </Wrapper>
  );
}

export default Footer;
