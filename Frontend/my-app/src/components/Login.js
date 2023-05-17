import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"

export default function Login(props) {

    const navigate = useNavigate()
 
  useEffect(()=> {
    if(!props.loginData.message && props.formData.username && props.formData.password){
        localStorage.setItem("token", props.loginData.token)
        localStorage.setItem("username", props.loginData.username)
        navigate('/profile');
    }
}, [props.loginData])

    function displayRegister(){
        const loginForm = document.querySelector('#loginForm')
        const registerForm = document.querySelector('#registerForm')
        loginForm.style.display = 'none'
        registerForm.style.display = 'flex'
    }

    function displayLogin(){
        const loginForm = document.querySelector('#loginForm')
        const registerForm = document.querySelector('#registerForm')
        loginForm.style.display = 'flex'
        registerForm.style.display = 'none'
    }

    return (
        <section className="login">
            <div className="login-header">
            </div>
            <div className="login-form">
            <h1>GreenBay</h1>
            <h3>Welcome to Greenbay</h3>
            <h4 className="error-message" style={props.loginData.message ? {display: 'block'} : {display: 'none'}}>{props.loginData.message}</h4>
            <form onSubmit={props.submitForm} method="POST" autoComplete="off" id="loginForm">
                <input name="username" type="text" placeholder="Username" onChange={props.handleChange} value={props.formData.name}/>
                <input name="password" type="password" placeholder="Password" onChange={props.handleChange} value={props.formData.name}/>
                <button type="submit">Login</button>
                <small>Are you new here? <span onClick={displayRegister}>Create an account</span></small>
            </form>
            <form onSubmit={props.register} id="registerForm">
                <input name="username" type="text" placeholder="Username" onChange={props.handleRegister} value={props.registerData.name}/>
                <input name="password" type="password" placeholder="Password" onChange={props.handleRegister} value={props.registerData.name}/>
                <button type="submit">Sign Up</button>
                <small>Already have an account? <span onClick={displayLogin}>Sign In</span></small>
            </form>
        </div>
        </section>
    )
}