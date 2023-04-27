import logo from './logo.svg';
import './App.css';
import './style.scss';
import React from 'react'
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Home from './components/Home';
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

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar />}>
          <Route index element={<Home />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="login" element={<Login />} />
          <Route path="test" element={<Test />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
