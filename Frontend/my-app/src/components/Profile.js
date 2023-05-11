import { useState } from "react"
import Product from "./Product"
import { Link, useNavigate } from "react-router-dom"

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

    const [uploadMessage, setUploadMessage] = useState()

    const navigate = useNavigate()
    
    async function addProduct(e){
        e.preventDefault()
        const res = await fetch('http://localhost:8080/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(productData)
        })
        const data = await res.json()
        props.displayAllProducts()
        props.displayProfileInformations()
        setUploadMessage(data.message)
        if(res.status === 201){
            cancelItem()
            navigate(`/products/${data.productId}`)
        }
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
    }

    let uploadedProducts
    let uploadedProductsCount

    if(props.userProfile){

        uploadedProducts = props.userProfile.uploadedProducts.map(function(item){
            return <div className="product" key={item.id}>
                <Link to={`/products/${item.id}`}>
                <div className="product-img">
                    <img src={item.photoUrl} alt="" />
                </div>
                <div className="product-text">
                <h3>{item.name}</h3>
                </div>
                </Link>
                <button className="delete-btn" onClick={() => deleteProduct(item.id)}><i className="fa-solid fa-xmark"></i></button>
            </div>
        })

        uploadedProductsCount = props.userProfile.uploadedProductsCount
    }

    function deleteProduct(id){
        fetch(`http://localhost:8080/products/${id}`, {
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

    function deleteButton(){
        const deleteBtn = document.querySelectorAll('.delete-btn')
        deleteBtn.forEach(item => {
            item.style.display = item.style.display == 'block' ? 'none' : 'block'
        });
    }

    return(
        <section className="profile">
            <div className="container">
            <div className="add-item-animation" onClick={displayForm}>
                <h1>Create new item</h1>
                <h1>+</h1>
            </div>
            <form onSubmit={addProduct} autoComplete="off" id="addProductForm">
                <h4 className="error-message" style={uploadMessage ? {display: 'block'} : {display: 'none'}}>{uploadMessage}</h4>
                <input type="text" name="name" onChange={handleItem} placeholder="Title"/>
                <input type="text" name="description" onChange={handleItem} placeholder="Description"/>
                <input type="text" name="photoUrl" onChange={handleItem} placeholder="Photo"/>
                <input type="number" name="purchasePrice" onChange={handleItem} placeholder="Purchase Price" step=".01"/>
                <input type="number" name="startingPrice" onChange={handleItem} placeholder="Starting Price" step=".01"/>
                <div className="button-container">
                    <button type="button" onClick={cancelItem}>Cancel</button>
                    <button type="submit">Add Item</button>
                </div>
            </form>
            <div className="profile-text">
                <h1>Your Items ({uploadedProductsCount})</h1>
                <button onClick={deleteButton}>Szerkesztés</button>
            </div>
            <div className="products">
                {uploadedProducts}
            </div>
            </div>
        </section>
    )
}