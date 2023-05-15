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
import Product from './components/Product';
import { Link } from 'react-router-dom'

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
    }
    const res = await fetch(fetchUrl)
    const data = await res.json()
    setProducts(data)
  }

  useEffect(() => {
    displayAllProducts()
  }, [])

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

  let displayItems = ""

  function displayAllItems(){
      if(products){
          displayItems = products.map(function(product){
              return <div key={product.id}>
              <Product description={product.description} 
              title={product.name} 
              image={product.photoUrl} 
              purchasePrice={product.purchasePrice} 
              startingPrice={product.startingPrice} />
              <Link to={`/products/${product.id}`}>More information</Link>
              </div>
          })
      }
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar userProfile={userProfile}/>}>
          <Route index element={<Home products={products} displayAllItems={displayAllItems} displayItems={displayItems}/>} />
          <Route path="login" element={<Login loginData={loginData} submitForm={submitForm} handleChange={handleChange} formData={formData} />} />
          <Route path="profile" element={<Profile products={products} displayAllProducts={displayAllProducts} userProfile={userProfile} displayProfileInformations={displayProfileInformations} />} />
          <Route path="products/:productId" element={<SingleProduct products={products} />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
