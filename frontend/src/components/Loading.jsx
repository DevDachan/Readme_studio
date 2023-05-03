import React from 'react';
import ReactLoading from 'react-loading';

function Loading({type, message,cancle}) {
  return (
    <div className="contentWrap">
      <div className="div-loading" >
        <h3 className="h3-loading">{message}</h3>
        <ReactLoading
          type={type}
          style={{margin:"auto",
          height:'60%',
          width:'60%',
          color:"white"
          }}
          />
        <div className="div-loading-cancle">
          <button className="btn-loading-cancle" onClick={cancle}> 취소 </button>
        </div>
      </div>
    </div>
  );
}

export default Loading;
