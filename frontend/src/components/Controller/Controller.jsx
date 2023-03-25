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


function Controller(props) {
  const navigate = useNavigate();
  const project_id = props.project_id;
  const setContent = props.setContent;
  const content = props.content;
  const currentReadme = props.currentReadme;
  const controllerList = props.controllerList;

  const list = [];

  const submitContributor = (e) =>{
    const formData = new FormData();
    const tempReadme = content;

    formData.append('project_id', project_id);
    formData.append('framework_name', e.target.value);
    console.log(e.target.value);

    axios({
      method: "post",
      url: 'http://localhost:8090/framework',
      data: formData,
    })
      .then(function (response){
<<<<<<< HEAD
        console.log("result : ", response.data); // frame content
=======
        console.log("result : ", response.data);
>>>>>>> 079e2369a85eb2d5220b9e3d15c58f2a073c9df8
        tempReadme.find(e => e.id === currentReadme).content = [...content.find(e => e.id === currentReadme).content, response.data];
        setContent(tempReadme);
      })
      .catch(function(error){
        //handle error
        tempReadme.find(e => e.id === currentReadme).content = [...content.find(e => e.id === currentReadme).content, "response.data"];
        setContent(tempReadme);
      })
      .then(function(){
        // always executed
      });
  }


  for(var i = 0; i< controllerList.length; i++){
    list.push(<input type="button" className="mb-2" key={controllerList[i]} value={controllerList[i]} onClick={submitContributor}/>);
  }
  return (
      <Wrapper>
        <div className="controller-div row">
          <h3 className="controoler-title"> Controller</h3>
          {list}
        </div>
      </Wrapper>
  );
}

export default Controller;
