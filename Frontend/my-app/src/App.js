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

  const [registerData, setRegisterData] = useState({
      username: '',
      email: '',
      password: ''
  })

  function handleRegister(e){
      const name = e.target.name;
      const value = e.target.value;

      setRegisterData({
          ...registerData,
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

  async function register(e){
    e.preventDefault()
    const res = await fetch('http://localhost:8080/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(registerData)
    })
    const data = res.json()
    console.log(res, data);
    if(res.status === 200){
      const loginForm = document.querySelector('#loginForm')
      const registerForm = document.querySelector('#registerForm')
      loginForm.style.display = 'flex'
      registerForm.style.display = 'none'
      loginForm.reset()
      registerForm.reset()
    }
  }

  const [products, setProducts] = useState([])

  async function displayAllProducts(){
    let url = new URL(window.location.href)
    const sort = url.searchParams.get("sort")
    window.history.replaceState({}, "", url.toString())
    let fetchUrl
    if(sort === 'desc'){
      fetchUrl = 'http://localhost:8080/products/sort/desc'
    } else if (sort === 'asc'){
      fetchUrl = 'http://localhost:8080/products/sort/asc'
    } else {
      fetchUrl = 'http://localhost:8080/api/products/all'
      window.history.replaceState({}, "", url.origin + url.pathname)
    }
    const res = await fetch(fetchUrl)
    const data = await res.json()
    setProducts(data)
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

  async function uploadFunds(fund){
    const res = await fetch('http://localhost:8080/balance', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
    },
    body: JSON.stringify({balance: fund})
    })
    const data = await res.json()
    displayProfileInformations()
}

  const [searchResult, setSearchResult] = useState()

  async function searchBar(result){
    const res = await fetch(`http://localhost:8080/products/search?search=${result}`)
    const data = await res.json()
    setSearchResult(data)
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar userProfile={userProfile} searchBar={searchBar} />}>
          <Route index element={<Home products={products} displayAllProducts={displayAllProducts} searchResult={searchResult} displayProfileInformations={displayProfileInformations}/>} />
          <Route path="login" element={<Login loginData={loginData} submitForm={submitForm} handleChange={handleChange} formData={formData} register={register} registerData={registerData} handleRegister={handleRegister}/>} />
          <Route path="profile" element={<Profile products={products} displayAllProducts={displayAllProducts} userProfile={userProfile} displayProfileInformations={displayProfileInformations} uploadFunds={uploadFunds}/>} />
          <Route path="products/:productId" element={<SingleProduct products={products} />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
