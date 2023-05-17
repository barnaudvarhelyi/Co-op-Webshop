import { useState, useEffect } from "react"
import Product from "./Product"
import { Link, useNavigate } from "react-router-dom"

export default function Profile(props){

    useEffect(() => {
        props.displayProfileInformations()
    }, [])

    const [productData, setProductData] = useState(
        {
            name: "",
            description: "",
            photoUrl: "",
            purchasePrice: "",
            startingPrice: ""
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
        document.getElementById("addItem").style.display = "block"
        document.getElementById("editItem").style.display = "none"
        const url = new URL(window.location.href)
        window.history.replaceState({}, "", url.origin + url.pathname)
        productForm.reset()
    }

    let uploadedProducts
    let uploadedProductsCount
    let balance

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
                <button className="edit-btn" onClick={() => editProduct(item.id)}><i className="fa-solid fa-gear"></i></button>
            </div>
        })

        uploadedProductsCount = props.userProfile.uploadedProductsCount
        balance = props.userProfile.balance.balance

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

    async function editProduct(id){
        const res = await fetch(`http://localhost:8080/products/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            },
            })
            const data = await res.json()
            displayForm()
            document.getElementById('addProductForm').scrollIntoView({
                behavior: "smooth"
            })
            document.getElementById("title").value = data.name
            document.getElementById("description").value = data.description
            document.getElementById("photoUrl").value = data.photoUrl
            document.getElementById("purchasePrice").value = data.purchasePrice
            document.getElementById("startingPrice").value = data.startingPrice
            document.getElementById("addItem").style.display = "none"
            document.getElementById("editItem").style.display = "block"
            let url = new URL(window.location.href)
            url.searchParams.set("id", id)
            window.history.replaceState({}, "", url.toString())
            console.log(url);
    }

    function editButton(){
        const deleteBtn = document.querySelectorAll('.delete-btn')
        const editBtn = document.querySelectorAll('.edit-btn')
        deleteBtn.forEach(item => {
            item.style.display = item.style.display == 'block' ? 'none' : 'block'
        });
        editBtn.forEach(item => {
            item.style.display = item.style.display == 'block' ? 'none' : 'block'
        });
    }

    async function editItem(){
        const url = new URL(window.location.href)
        const id = url.searchParams.get('id')
        let inputValues = {
            name: document.getElementById("title").value,
            description: document.getElementById("description").value,
            photoUrl: document.getElementById("photoUrl").value,
            startingPrice: document.getElementById("startingPrice").value,
            purchasePrice: document.getElementById("purchasePrice").value,
        }
        const res = await fetch(`http://localhost:8080/products/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(inputValues)
            })
            const data = await res.json()
            navigate(`/products/${id}`)
            document.getElementById("addItem").style.display = "block"
            document.getElementById("editItem").style.display = "none"
    }

    function addFunds(){
        const addFunds = document.querySelector('.add-funds')
        const cancelItem = document.querySelector('.cancel-fund')
        const addItem = document.querySelector('.add-item-animation')
        addFunds.style.display = addFunds.style.display == 'flex' ? 'none' : 'flex'
        cancelItem.style.display = cancelItem.style.display == 'flex' ? 'none' : 'flex'
        addItem.style.display = addItem.style.display == 'none' ? 'flex' : 'none'
    }

    return(
        <section className="profile">
            <div className="container">
            <h3>Balance: {balance}</h3>
            <div className="add-item-animation btn-grad">
                <div onClick={displayForm} className="btn-grad">
                    <h1>Create new item</h1>
                    <h1>+</h1>
                </div>
                <div onClick={addFunds} className="btn-gradient">
                    <h1>Add funds to your balance</h1>
                    <h1>+</h1>
                </div>
            </div>
            <div className="add-funds">
                <div onClick={() => props.uploadFunds(10)}>$10</div>
                <div onClick={() => props.uploadFunds(50)}>$50</div>
                <div onClick={() => props.uploadFunds(100)}>$100</div>
            </div>
            <h4 className="cancel-fund" onClick={addFunds}>Cancel</h4>
            <form onSubmit={addProduct} autoComplete="off" id="addProductForm">
                <h4 className="error-message" style={uploadMessage ? {display: 'block'} : {display: 'none'}}>{uploadMessage}</h4>
                <input type="text" name="name" onChange={handleItem} placeholder="Title" id="title"/>
                <input type="text" name="description" onChange={handleItem} placeholder="Description" id="description"/>
                <input type="text" name="photoUrl" onChange={handleItem} placeholder="Photo" id="photoUrl"/>
                <input type="number" name="purchasePrice" onChange={handleItem} placeholder="Purchase Price" step=".01" id="purchasePrice"/>
                <input type="number" name="startingPrice" onChange={handleItem} placeholder="Starting Price" step=".01" id="startingPrice"/>
                <div className="button-container">
                    <button type="button" onClick={cancelItem}>Cancel</button>
                    <button type="submit" id="addItem">Add Item</button>
                    <button type="button" id="editItem" onClick={editItem}>Edit Item</button>
                </div>
            </form>
            <div className="profile-text">
                <h1>Your Items ({uploadedProductsCount})</h1>
                <button onClick={editButton}><i className="fa-solid fa-gear"></i>Edit</button>
            </div>
            <div className="products">
                {uploadedProducts}
            </div>
            </div>
        </section>
    )
}