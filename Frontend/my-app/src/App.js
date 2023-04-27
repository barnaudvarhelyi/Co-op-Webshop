import logo from './logo.svg';
import './App.css';
import './style.scss';
import React from 'react'
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Home from './components/Home';
import { useState, useEffect } from "react"
import Test from './components/Test'
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';
import Login from './components/Login';

function App() {

  const [data, setData] = React.useState({})

  React.useEffect(() => {
    fetch(`http://localhost:8080/api/users/all`)
      .then(res => res.json())
      .then(data => setData(data))
  }, [])
  
  const [loginData, setLoginData] = useState([])

  const [formData, setFormData] = useState({
      username: '',
      password: ''
  })

  function handleChange(e){
      const name = e.target.name;
      const value = e.target.value;

      setFormData({
          ...formData,
          [name]: value
      })
  }

  function submitForm(e){
      e.preventDefault()

      fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
      })
      .then(res => res.json())
      .then(data => setLoginData(data))
      .catch(err => console.log("Error: " + err))
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar />}>
          <Route index element={<Home />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="login" element={<Login loginData={loginData} submitForm={submitForm} handleChange={handleChange} formData={formData}/>} />
          <Route path="test" element={<Test formData={formData}/>} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
