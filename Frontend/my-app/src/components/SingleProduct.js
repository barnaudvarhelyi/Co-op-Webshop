import { Link, useParams } from 'react-router-dom'
import { useState, useEffect } from 'react';

export default function SingleProduct(props){

    const { productId } = useParams()
    const [productPage, setProductPage] = useState()
    // const product = props.products.find((product) => product.id == productId);
    async function singlePageProduct(){
        const res = await fetch(`http://localhost:8080/products/${productId}`)
        const data = await res.json()
        setProductPage(data)
    }
    
    useEffect(() => {
        singlePageProduct()
    }, [])
    // const { name, photoUrl, startingPrice, purchasePrice, description } = product

    if(productPage != undefined){
    return (
        <div className="single-product">
            <Link to="/">Back to Home Page</Link>
            <h3>Uploaded by: {productPage.uploder}</h3>
            <h1>{productPage.name}</h1>
            <img src={productPage.photoUrl} alt={productPage.name} />
            <h3>Starting Price: ${productPage.startingPrice}</h3>
            <h3>Purchase Price: ${productPage.purchasePrice}</h3>
            <h3>Description</h3>
            <p>{productPage.description}</p>
        </div>
    )
    }
}