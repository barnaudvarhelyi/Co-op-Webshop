import { Link, useParams } from 'react-router-dom'
import { useState, useEffect } from 'react';

export default function SingleProduct(props){

    const { productId } = useParams()
    const product = props.products.find((product) => product.id == productId);
    const { name, photoUrl, startingPrice, purchasePrice, description } = product

    return (
        <div className="single-product">
            <Link to="/">Back to Home Page</Link>
            <h1>{name}</h1>
            <img src={photoUrl} alt={name} />
            <h3>Starting Price: ${startingPrice}</h3>
            <h3>Purchase Price: ${purchasePrice}</h3>
            <h3>Description</h3>
            <p>{description}</p>
        </div>
    )
}