import { Link } from "react-router-dom"
import Product from './Product';
import { useState, useEffect } from "react"

export default function Home() {

    const [products, setProducts] = useState()

    useEffect(() => {
        fetch(`http://localhost:8080/api/products/all`)
          .then(res => res.json())
          .then(data => setProducts(data))
      }, [])

    console.log(products);

    return (
        <div>
            <h1>Home Page</h1>
            <Link to="/login">Login</Link>
            {}
        </div>
    )
}