import { useState } from "react"
import Product from "./Product"
import { Link } from "react-router-dom"

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
        if (message === undefined && productData.name && productData.description && productData.photoUrl && productData.startingPrice && productData.purchasePrice) {
            cancelItem()
        }
        setUploadMessage(message)
    }
    
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
        console.log(productData);
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

    let uploadedProducts
    let uploadedProductsCount

    if(props.userProfile){

        uploadedProducts = props.userProfile.uploadedProducts.map(function(item){
            console.log(item.id);
            return <div key={item.id}>
                <Product title={item.name} />
                <button onClick={() => deleteProduct(item.id)}>Törlés</button>
                <Link to={`/products/${item.id}`}>More information</Link>
            </div>
        })

        uploadedProductsCount = props.userProfile.uploadedProductsCount
    }

    function deleteProduct(id){
        fetch(`http://localhost:8080/products/delete/${id}`, {
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
                <input type="number" name="purchasePrice" onChange={handleItem} placeholder="Purchase Price" step=".01"/>
                <input type="number" name="startingPrice" onChange={handleItem} placeholder="Starting Price" step=".01"/>
                <div className="button-container">
                    <button type="button" onClick={cancelItem}>Cancel</button>
                    <button type="submit">Add Item</button>
                </div>
            </form>
            {uploadedProductsCount}
            {uploadedProducts}
            </div>
        </section>
    )
}