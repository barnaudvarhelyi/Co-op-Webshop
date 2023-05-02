import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"

export default function Login(props) {

    const navigate = useNavigate()


  useEffect(()=> {
    if(!props.loginData.message && props.formData.username && props.formData.password)
    navigate('/test');
    localStorage.setItem("username", props.formData.username)
    localStorage.setItem("token", props.loginData.token)
}, [props.loginData, navigate])

    return (
        <section className="login">
            <div className="login-header">
            </div>
            <div className="login-form">
            <h1>GreenBay</h1>
            <h3>Welcome to Greenbay</h3>
            <h4 className="error-message" style={props.loginData.message ? {display: 'block'} : {display: 'none'}}>{props.loginData.message}</h4>
            <form onSubmit={props.submitForm} method="POST">
                <input name="username" type="text" placeholder="Username" onChange={props.handleChange} value={props.formData.name}/>
                <input name="password" type="password" placeholder="Password" onChange={props.handleChange} value={props.formData.name}/>
                <button type="submit">Login</button>
            </form>
        </div>
        </section>
    )
}