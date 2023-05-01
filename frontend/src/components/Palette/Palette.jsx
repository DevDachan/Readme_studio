import React, { useState , useEffect} from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

const Wrapper = styled.div`
    padding: 30px;
    margin: 0;
    width: 100%;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    overflow:auto;
    border-right: 2px solid #D1D5DB;
    min-width: 230px;
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
  const defaultData = props.defaultData;
  const list = [];


  const addContent = (e) =>{
    const formData = new FormData();

    var tempReadme = JSON.parse(JSON.stringify(content));
    formData.append('project_id', project_id);
    formData.append('framework_name', e.target.value);

    axios({
      method: "post",
      url: 'http://localhost:8090/framework',
      data: formData,
    })
      .then(function (response){
        if(e.target.value == "Header"){
          tempReadme.find(e => e.id === currentReadme).content.splice(0,0, response.data);
          tempReadme.find(e => e.id === currentReadme).type.splice(0,0,e.target.value);
          setContent(tempReadme);
          setPosition(1);
          setTimeout(function() {
             document.getElementById("postionChangeDown"+(0)).focus();
           }, 1);
        }else{
          tempReadme.find(e => e.id === currentReadme).content.splice(position,0, response.data);
          tempReadme.find(e => e.id === currentReadme).type.splice(position,0,e.target.value);
          setContent(tempReadme);
          setPosition(Number(position)+1);
          setTimeout(function() {
             document.getElementById("postionChangeDown"+(position)).focus();
           }, 1);
        }
      })
      .catch(function(error){
        //handle error
        console.log(error);
      });
  }


  const emptyText = (e) => {
    var tempReadme = JSON.parse(JSON.stringify(content));
    let emptyText = "<!-- empty_textarea -->\n";
    tempReadme.find(e => e.id === currentReadme).content.splice(position,0, emptyText);
    tempReadme.find(e => e.id === currentReadme).type.splice(position,0,"Text");
    setContent(tempReadme);
    // 렌더링이 되지 않은 상태에서 scroll이 일어날 경우 이동이 안된다.
    setPosition(tempReadme.find(e => e.id === currentReadme).content.length);
    setTimeout(function() {
       document.getElementById("select_"+(position-1)).scrollIntoView(true);
     }, 0);
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
        tempReadme.find(e => e.id === currentReadme).content = [];
        tempReadme.find(e => e.id === currentReadme).type = [];

        for(var i = 0; i < response.data.content.length; i++){
          tempReadme.find(e => e.id === currentReadme).content.push(response.data.content[i]);
          tempReadme.find(e => e.id === currentReadme).type.push(response.data.type[i]);
        }

        tempReadme.find(e => e.id === currentReadme).content.splice(1,0,defaultData);
        tempReadme.find(e => e.id === currentReadme).type.splice(1,0,"Default Data");


        setContent(tempReadme);
        setPosition(tempReadme.find(e => e.id === currentReadme).content.length);
        setTimeout(function() {
           document.getElementById("select_"+(position-1)).scrollIntoView(true);
         }, 0);
      })
      .catch(function(error){
        //handle error
        console.log(error);
      });
  }

  // 기본 All Data와 Text 팔레트 추가
  list.push(<input type="button" className="mb-2 btn-palette" key={"all_data"} value={"All Data"} onClick={allData}/>);
  list.push(<input type="button" className="mb-2 btn-palette" key={"empty_textarea"} value={"Text"} onClick={emptyText}/>);

  // DB에 저장되어 있는 framework 팔레트 추가
  for(var i = 0; i< paletteList.length; i++){
    list.push(<input type="button" className="mb-2 btn-palette" key={paletteList[i]} value={paletteList[i]} onClick={addContent}/>);
  }

  return (
      <Wrapper>
        <h3 className="palette-optionsTitle"> Options</h3>
        <div className="palette-div row">
          <h3 className="palette-title"> Palette</h3>
          {list}
        </div>
      </Wrapper>
  );
}

export default Palette;
