import { useState } from "react"

export default function Login() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const ProceedLogin = (e) => {
        e.preventDefault()
        if(validate()){
            fetch("http://localhost:8080/login", {
                method: 'POST',
                mode: "no-cors",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify({
                    'username': username,
                    'password': password})
            }).then((res) =>  res.json()
            ).then((result) => {
                console.log(result)
            }).catch((err) => {
                console.log('Login Failed due to : '+ err.message);
            })
        }
    }

    const validate = () => {
        let result = true
        if(username === '' || username === null){
            result = false;
            console.log("Please enter username");
        }
        if(password === '' || password === null){
            result = false;
            console.log("Please enter password");
        }
        return result
    }

    return (
        <div>
            <form onSubmit={ProceedLogin} method="POST">
                <input value={username} onChange={e => setUsername(e.target.value)} name="username" />
                <input type="password" value={password} onChange={e => setPassword(e.target.value)} name="password" />
                <button type="submit">Login</button>
            </form>
        </div>
    )
}