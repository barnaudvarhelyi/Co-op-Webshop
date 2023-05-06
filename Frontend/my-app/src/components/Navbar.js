import { Outlet, Link } from "react-router-dom"

export default function Navbar(){

    let loggedIn = localStorage.getItem("username")
    
    return (
        <section className="navbar">
        <nav>
            <div className="container">
                <Link to="/"><h1>Greenbay</h1></Link>
                <Link to={loggedIn ? "/profile" : "/login"}>{loggedIn ? loggedIn : "Login"}</Link>
            </div>
        </nav>
        <Outlet />
        </section>

    )
}