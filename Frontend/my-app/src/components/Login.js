import { useState } from "react"

export default function Login() {

    const [formData, setFormData] = useState({
        username: '',
        password: ''
    })

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
        .then(data => console.log(data))
        .catch(err => console.log("Error: " + err))
    }

    return (
        <div>
            <form onSubmit={submitForm} method="POST">
                <input name="username" type="text" placeholder="username" onChange={handleChange} value={formData.name}/>
                <input name="password" type="password" placeholder="password" onChange={handleChange} value={formData.name}/>
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}