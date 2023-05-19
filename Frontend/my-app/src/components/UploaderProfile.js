import Login from "./Login"
import { useState, useEffect } from "react"
import { useParams } from "react-router-dom"

export default function UploaderProfile(props){

  const [uploaderProfile, setUploaderProfile] = useState()

  const { username } = useParams()

  async function displayUploader(){
    const res = await fetch(`http://localhost:8080/user-profile/${username}`)
    const data = await res.json()
    setUploaderProfile(data)
  }

  useEffect(() => {
    displayUploader()
  }, [])

  if(uploaderProfile){
    console.log(uploaderProfile.username, uploaderProfile.email, uploaderProfile.products);
  }

    return (
        <div>
            
        </div>
    )
}