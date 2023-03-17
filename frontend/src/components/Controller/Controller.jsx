import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

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
  const [index, setIndex] = useState(props.index);
  const setContent = props.setContent;
  const constrollerList = props.constrollerList;
  const prevContent = props.prevContent;
  const list = [];


  const submitContributor = (e) =>{
    const formData = new FormData();

    formData.append('index', index);
    formData.append('template_id', e.value);

    axios({
      method: "post",
      url: 'http://localhost:8090/template',
      data: formData,
    })
      .then(function (response){
        console.log("result : ", response.data);
        setContent([...prevContent, response.data]);
      })
      .catch(function(error){
        //handle error
        console.log(error);
        setContent([...prevContent, "hi"]);
      })
      .then(function(){
        // always executed
      });
  }


  for(var i = 0; i< constrollerList.length; i++){
    list.push(<input type="button" className="mb-2" value={constrollerList[i]} onClick={submitContributor}/>);
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

export default ReadmeFileContent;
