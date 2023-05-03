import React from 'react';
import ReactLoading from 'react-loading';

function Loading({type, message,cancel}) {
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
        <div className="div-loading-cancel">
          <button className="btn-loading-cancel" onClick={cancel("cancel")}> 취소 </button>
        </div>
      </div>
    </div>
  );
}

export default Loading;
