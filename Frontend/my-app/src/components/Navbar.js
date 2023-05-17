import { Outlet, Link } from "react-router-dom"
import { useState } from "react"

export default function Navbar(props){

    let loggedIn = localStorage.getItem("username")

    return (
        <section className="navbar">
        <nav>
            <div className="container">
                <Link to="/"><h1>Greenbay</h1></Link>               
                <input type="text" className="search-bar" placeholder="Start typing..." onChange={(e) => props.searchBar(e.target.value)}/>
                <Link to={loggedIn ? "/profile" : "/login"}>{loggedIn ? loggedIn : "Login"}</Link>
            </div>
        </nav>
        <Outlet />
        </section>

    )
}