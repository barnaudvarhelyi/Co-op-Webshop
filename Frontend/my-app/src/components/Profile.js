import { useState } from "react"
import Product from "./Product"
import { json, useNavigate } from "react-router-dom"

export default function Profile(props){

    const [productData, setProductData] = useState(
        {
            name: "",
            description: "",
            photoUrl: "",
            startingPrice: "",
            purchasePrice: ""
        }
    )

    function handleItem(e){
        const name = e.target.name
        const value = e.target.value

        setProductData({
            ...productData,
            [name]: value
        })
    }

    function clearProductData(){
        setProductData({
            name: "",
            description: "",
            photoUrl: "",
            startingPrice: "",
            purchasePrice: ""
        })
    }

    const [uploadMessage, setUploadMessage] = useState()

    function uploadedProduct(message){
        if (message == undefined && productData.name && productData.description && productData.photoUrl && productData.startingPrice && productData.purchasePrice) {
            cancelItem()
        }
        setUploadMessage(message)
    }

    const navigate = useNavigate()
    
    function addProduct(e){
        e.preventDefault()
        fetch('http://localhost:8080/products/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(productData)
        })
        .then(res => res.json())
        .then(data => {
            props.displayAllProducts()
            props.displayProfileInformations()
            const message = data.message
            uploadedProduct(message)
        })
        .catch(err => console.log("Error: " + err))
    }

    function displayForm(){
        const productForm = document.querySelector('#addProductForm')
        const addProduct = document.querySelector('.add-item-animation')
        productForm.style.display = 'flex'
        addProduct.style.display = 'none'
    }

    function cancelItem(){
        const productForm = document.querySelector('#addProductForm')
        const addProduct = document.querySelector('.add-item-animation')
        productForm.style.display = 'none'
        addProduct.style.display = 'flex'
        productForm.reset()
        clearProductData()
    }

    let username
    let uploadedProducts
    let uploadedProductsCount

    if(props.userProfile){
        username = props.userProfile.username

        uploadedProducts = props.userProfile.uploadedProducts.map(function(item){
            return <Product key={item.id} title={item.name} />
        })

        uploadedProductsCount = props.userProfile.uploadedProductsCount
    }

    function deleteProduct(e){
        e.preventDefault()
        fetch(`http://localhost:8080/products/delete/8`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        }
        })
        .then(res => res.json())
        .then(data => {
            props.displayAllProducts()
            props.displayProfileInformations()
        })
        .catch(err => console.log("Error: " + err))
    }

    return(
        <section className="profile">
            <div className="container">
            <div className="add-item-animation">
                <button onClick={displayForm}>Create new item</button>
                <button>+</button>
            </div>
            <form onSubmit={addProduct} autoComplete="off" id="addProductForm">
                <h4 className="error-message" style={uploadMessage ? {display: 'block'} : {display: 'none'}}>{uploadMessage}</h4>
                <input type="text" name="name" onChange={handleItem} placeholder="Title"/>
                <input type="text" name="description" onChange={handleItem} placeholder="Description"/>
                <input type="text" name="photoUrl" onChange={handleItem} placeholder="Photo"/>
                <input type="number" name="purchasePrice" onChange={handleItem} placeholder="Purchase Price"/>
                <input type="number" name="startingPrice" onChange={handleItem} placeholder="Starting Price"/>
                <button type="submit">Add Item</button>
                <br />
                <button type="button" onClick={cancelItem}>Cancel</button>
            </form>
            <button onClick={deleteProduct}>Törlés</button>
            {uploadedProductsCount}
            {uploadedProducts}
            </div>
        </section>
    )
}