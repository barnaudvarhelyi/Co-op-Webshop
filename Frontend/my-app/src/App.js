import logo from './logo.svg';
import './App.css';
import React from 'react'
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Navbar from './components/Navbar';

function App() {

  const [data, setData] = React.useState({})

  React.useEffect(() => {
    fetch(`http://localhost:8080/api/users/all`)
      .then(res => res.json())
      .then(data => setData(data))
  }, [])

  console.log(data);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navbar />}>
          <Route index element={<h1>Home</h1>} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
