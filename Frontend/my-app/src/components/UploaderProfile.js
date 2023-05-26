import Login from "./Login"
import { useState, useEffect } from "react"
import { useParams, Link } from "react-router-dom"

export default function UploaderProfile(props){

  const [uploaderProfile, setUploaderProfile] = useState()

  const { username } = useParams()

  /* When the user clicks on the uploaders name, it collects that user's data and saves it to uploaderProfile state */
  async function displayUploader(){
    const res = await fetch(`http://localhost:8080/user-profile/${username}`)
    const data = await res.json()
    setUploaderProfile(data)
    console.log(data);
  }

  useEffect(() => {
    displayUploader()
  }, [])

  let uploaderInformations

  /* Displays a user's informations */
  if(uploaderProfile){
    uploaderInformations = <div>
      <h1>{uploaderProfile.username}'s Profile</h1>
      <h4>{uploaderProfile.email}</h4>
      <h3>{uploaderProfile.username}'s uploaded products ({uploaderProfile.productsCount})</h3>
    </div>
  }

  let uploadersProducts

  /* Displays a user's uploaded products */
  if(uploaderProfile){
    uploadersProducts = uploaderProfile.products.map(function(item){
        return <div className="product" key={item.productId}>
            <Link to={`/products/${item.productId}`}>
            <div className="product-img">
                <img src={item.photoUrl} alt="" />
            </div>
            <div className="product-text">
            <h3>{item.name}</h3>
            </div>
            </Link>
        </div>
    })
}

    return (
        <div>
          {uploaderInformations}
          <div className="products">
            {uploadersProducts}
          </div>
        </div>
    )
}