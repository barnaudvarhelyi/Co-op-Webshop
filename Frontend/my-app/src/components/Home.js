import { Link, json } from "react-router-dom"
import { useState, useEffect } from "react"

export default function Home(props) {

    useEffect(() => {
        props.displayAllItems()
      }, [])

    function sortItems(sorting){
        let url = new URL(window.location.href)
        url.searchParams.set("sort", sorting)
        window.history.replaceState({}, "", url.toString())
        props.displayAllProducts()
    }
    
    return (
        <div>
            <button type="button" onClick={() => sortItems('desc')}>Sort by descending</button>
            <button type="button" onClick={() => sortItems('asc')}>Sort by ascending</button>
            <div className="products">
                {props.displayItems}
            </div>
        </div>
    )
}