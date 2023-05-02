import { Link, json } from "react-router-dom"
import Product from './Product';
import { useState, useEffect } from "react"

export default function Home() {

    // const [products, setProducts] = useState([])

    useEffect(() => {
        fetch('http://localhost:8080/api/products/all')
        .then(res => res.json())
        .then(data => console.log(data))
        .catch(err => console.log("Error: " + err))
    }, [])

    // let displayItems = ""

    // if(products){
    //      displayItems = products.map(function(product){
    //         return <Product key={product.id} title={product.name} price={product.startingPrice} />
    //     })
    // }

    const [productData, setProductData] = useState(
        {
            name: "",
            description: "",
            photoUrl: "",
            startingPrice: "",
            purchasePrice: ""
        }
    )

    function handleItem(e){
        const name = e.target.name
        const value = e.target.value

        setProductData({
            ...productData,
            [name]: value
        })

        console.log(productData);
    }
    
    function addProduct(e){ 
        fetch('http://localhost:8080/products/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(productData)
        })
        .then(res => {
            res.json()
            console.log(res);
        })
        .then(data => {
            setProductData(data)
            console.log(data);
        })
        .catch(err => console.log("Error: " + err))

        // console.log(products);
        console.log(productData);
    }
    
    return (
        <div>
            <h1>Home Page</h1>
            <Link to="/login">Login</Link>
            <form onSubmit={addProduct}>
                <input type="text" name="name" onChange={handleItem} placeholder="Title"/>
                <input type="text" name="description" onChange={handleItem} placeholder="Description"/>
                <input type="text" name="photoUrl" onChange={handleItem} placeholder="Photo"/>
                <input type="number" name="startingPrice" onChange={handleItem} placeholder="Starting Price"/>
                <input type="number" name="purchasePrice" onChange={handleItem} placeholder="Purchase Price"/>
                <button type="submit">Add Item</button>
            </form>
            {/* {displayItems} */}
        </div>
    )
}