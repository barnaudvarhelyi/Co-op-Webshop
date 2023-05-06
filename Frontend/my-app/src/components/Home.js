import { Link, json } from "react-router-dom"
import Product from './Product';
import { useState, useEffect } from "react"

export default function Home(props) {

    let displayItems = ""

    if(props.products){
        displayItems = props.products.map(function(product){
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
    
    return (
        <div>
            <div className="products">
                {displayItems}
            </div>
        </div>
    )
}