import React from 'react'
import { useState, useEffect } from "react"
import { BrowserRouter, Routes, Route } from "react-router-dom"
import './style.scss';
import Home from './components/Home';
import Login from './components/Login';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import SinglePageProduct from './components/SinglePageProduct';
import UploaderProfile from './components/UploaderProfile';

function App() {

  /* Login section */
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

  function login(e){
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

  /* Register section */
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
    if(res.status === 200){
      const loginForm = document.querySelector('#loginForm')
      const registerForm = document.querySelector('#registerForm')
      loginForm.style.display = 'flex'
      registerForm.style.display = 'none'
      loginForm.reset()
      registerForm.reset()
    }
  }

  /* Displaying products section */
  const [products, setProducts] = useState()

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
      fetchUrl = 'http://localhost:8080/products/all'
      window.history.replaceState({}, "", url.origin + url.pathname)
    }
    const res = await fetch(fetchUrl)
    const data = await res.json()
    setProducts(data)
    document.title = 'Greenbay'
  }

  /* Searchbar */
  const [searchResult, setSearchResult] = useState()

  async function searchBar(result){
    const res = await fetch(`http://localhost:8080/products/search?search=${result}`)
    const data = await res.json()
    setSearchResult(data)
  }

  /* User's profile section */
  const [userProfile, setUserProfile] = useState()

  async function displayProfileInformation(){
    const res = await fetch('http://localhost:8080/profile', {
      headers: {
          'Authorization': `Bearer ${localStorage.getItem("token")}`
      }
    })
    const data = await res.json()
    setUserProfile(data)
    document.title = `${data.username} | Greenbay`
  }

  /* Updating balance section */
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
    displayProfileInformation()
}

  /* Purchase item */
  async function purchase(productId){
    const res = await fetch(`http://localhost:8080/purchase?productId=${productId}`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem("token")}`
      }
    })
    const data = await res.json()
  }

  /* Place a bid */
  async function placeBid(productId){
    const bidAmount = document.getElementById('bidAmount').value
    const res = await fetch(`http://localhost:8080/bid?productId=${productId}`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem("token")}`
      },
      body: JSON.stringify({amount: bidAmount})
    })
    const data = await res.json()
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar userProfile={userProfile} searchBar={searchBar} />}>
          <Route index element={<Home products={products} displayAllProducts={displayAllProducts} searchResult={searchResult} displayProfileInformation={displayProfileInformation}/>} />
          <Route path="login" element={<Login login={login} loginData={loginData} handleChange={handleChange} formData={formData} register={register} handleRegister={handleRegister} registerData={registerData}/>} />
          <Route path="profile" element={<Profile products={products} displayAllProducts={displayAllProducts} userProfile={userProfile} displayProfileInformation={displayProfileInformation} uploadFunds={uploadFunds}/>} />
          <Route path="products/:productId" element={<SinglePageProduct products={products} purchase={purchase} placeBid={placeBid} />}/>
          <Route path="user/:username" element={<UploaderProfile />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
