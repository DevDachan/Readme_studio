import logo from './logo.svg';
import './App.css';

import React from "react";
import{
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";


import NotFound from "./components/NotFound";
import Main from "./components/Main";
import Footer from "./components/Footer";
import Editor from "./components/Editor";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />}></Route>
        <Route path="/editor" element={<Editor />}> </Route>
        <Route path="*" element={<NotFound />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
