import React, { useState , useEffect} from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

const Wrapper = styled.div`
    padding: 0;
    margin: 0 auto;
    display: flex;
    width: 70%;
    flex-direction: column;
    justify-content: center;
    text-align: center;
`;


function Palette(props) {
  const navigate = useNavigate();
  const project_id = props.project_id;
  const setContent = props.setContent;
  const content = props.content;
  const currentReadme = props.currentReadme;
  const paletteList = props.paletteList;
  const position = props.position;
  const setPosition = props.setPosition;
  const list = [];


  const submitContributor = (e) =>{
    const formData = new FormData();
    let tempReadme = content;

    formData.append('project_id', project_id);
    formData.append('framework_name', e.target.value);

    axios({
      method: "post",
      url: 'http://localhost:8090/framework',
      data: formData,
    })
      .then(function (response){
        tempReadme.find(e => e.id === currentReadme).content.splice(position,0, response.data);

        setContent(tempReadme);
        setPosition(tempReadme.find(e => e.id === currentReadme).content.length);
      })
      .catch(function(error){
        //handle error
        console.log(error);
      })
      .then(function(){
        // always executed
      });
  }


  const emptyText = (e) => {
    let tempReadme = content;
    let emptyText = "<!-- empty_textarea -->\n";
    tempReadme.find(e => e.id === currentReadme).content.splice(position,0, emptyText);

    setContent(tempReadme);
    setPosition(tempReadme.find(e => e.id === currentReadme).content.length);
  }

  const allData = (e) => {
    let tempReadme = content;
    const formData = new FormData();

    formData.append("project_id", project_id);
    axios({
      method: "post",
      url: 'http://localhost:8090/alldata',
      data: formData,
    })
      .then(function (response){
        tempReadme.find(e => e.id === currentReadme).content = ["<!-- All Data -->\n"+response.data];
        setContent(tempReadme);
      })
      .catch(function(error){
        //handle error
        console.log(error);
      })
      .then(function(){
        // always executed
      });
  }

  list.push(<input type="button" className="mb-4 btn-3d yellow" key={"all_data"} value={"All Data"} onClick={allData}/>);
  list.push(<input type="button" className="mb-4 btn-3d green" key={"empty_textarea"} value={"Text"} onClick={emptyText}/>);


  for(var i = 0; i< paletteList.length; i++){
    list.push(<input type="button" className="mb-4 btn-3d green" key={paletteList[i]} value={paletteList[i]} onClick={submitContributor}/>);
  }
  return (
      <Wrapper>
        <div className="palette-div row">
          <h3 className="palette-title"> Palette</h3>
          {list}
        </div>
      </Wrapper>
  );
}

export default Palette;
