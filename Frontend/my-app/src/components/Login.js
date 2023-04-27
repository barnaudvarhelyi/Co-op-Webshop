import { useState } from "react"
import { useNavigate } from "react-router-dom"

export default function Login() {

    const [loginData, setLoginData] = useState([])

    const [formData, setFormData] = useState({
        username: '',
        password: ''
    })

    const navigate = useNavigate()

    function handleChange(e){
        const name = e.target.name;
        const value = e.target.value;

        setFormData({
            ...formData,
            [name]: value
        })
    }

    function submitForm(e){
        e.preventDefault()

        fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
        })
        .then(res => res.json())
        .then(data => setLoginData(data))
        .catch(err => console.log("Error: " + err))
    }

    if(!loginData.message && formData.username && formData.password)
    navigate('/test');

    return (
        <section className="login">
            <div className="login-header">
            </div>
            <div className="login-form">
            <h1>GreenBay</h1>
            <h3>Welcome to Greenbay</h3>
            <h4 className="error-message" style={loginData.message ? {display: 'block'} : {display: 'none'}}>{loginData.message}</h4>
            <form onSubmit={submitForm} method="POST">
                <input name="username" type="text" placeholder="Username" onChange={handleChange} value={formData.name}/>
                <input name="password" type="password" placeholder="Password" onChange={handleChange} value={formData.name}/>
                <button type="submit">Login</button>
            </form>
            </div>
        </section>
    )
}