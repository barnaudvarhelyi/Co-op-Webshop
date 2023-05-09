import logo from './logo.svg';
import './App.css';
import './style.scss';
import React from 'react'
import { useState, useEffect } from "react"
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Navbar from './components/Navbar';
import Home from './components/Home';
import Login from './components/Login';
import Profile from './components/Profile';
import SingleProduct from './components/SingleProduct';

function App() {

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

  const [products, setProducts] = useState([])

  useEffect(() => {
    displayAllProducts()
    displayProfileInformations()
  }, [])

  function displayAllProducts(){
    fetch('http://localhost:8080/api/products/all')
    .then(res => res.json())
    .then(data => setProducts(data))
    .catch(err => console.log("Error: " + err))
  }

  const [userProfile, setUserProfile] = useState()

  function displayProfileInformations(){
    fetch('http://localhost:8080/profile', {
      headers: {
          'Authorization': `Bearer ${localStorage.getItem("token")}`
      }
    })
    .then(res => res.json())
    .then(data => setUserProfile(data))
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar/>}>
          <Route index element={<Home products={products}/>} />
          <Route path="login" element={<Login loginData={loginData} submitForm={submitForm} handleChange={handleChange} formData={formData} />} />
          <Route path="profile" element={<Profile products={products} displayAllProducts={displayAllProducts} userProfile={userProfile} displayProfileInformations={displayProfileInformations} />} />
          <Route path="products/:productId" element={<SingleProduct products={products} />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
