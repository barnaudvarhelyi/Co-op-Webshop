import { Link, json } from "react-router-dom"
import { useState, useEffect } from "react"
import Product from './Product';

export default function Home(props) {

    useEffect(() => {
        props.displayAllProducts()
      }, [])

    let displayItems = ""

    if(props.searchResult){
        displayItems = props.searchResult.map(function(searchResult){
            return <div key={searchResult.id}>
            <Product description={searchResult.description} 
            title={searchResult.name} 
            image={searchResult.photoUrl} 
            purchasePrice={searchResult.purchasePrice} 
            startingPrice={searchResult.startingPrice} />
            <Link to={`/products/${searchResult.id}`}>More information</Link>
            </div>
        })
    } else if(props.products){
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

    function sortItems(sorting){
        let url = new URL(window.location.href)
        url.searchParams.set("sort", sorting)
        window.history.replaceState({}, "", url.toString())
        props.displayAllProducts()
    }
    
    return (
        <div>
            <div className="sort-items">
            <select onChange={(e) => sortItems(e.target.value)}>
                <option value="">Sort by default</option>
                <option value="asc">Sort by ascending</option>
                <option value="desc">Sort by descending</option>
            </select>
            </div>
            <div className="products">
                {displayItems}
            </div>
        </div>
    )
}