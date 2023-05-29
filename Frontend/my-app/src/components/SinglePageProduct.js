import { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom'
import Product from './Product';
import { Swiper, SwiperSlide } from 'swiper/react'
import 'swiper/css'

export default function SingleProduct(props){

    const { productId } = useParams()
    const [productPage, setProductPage] = useState()

    /* When the user clicks on a product, it collects that product's data and saves it to productPage state */
    async function singlePageProduct(){
        const res = await fetch(`http://localhost:8080/products/${productId}`)
        const data = await res.json()
        setProductPage(data)
        document.title = `${data.name} | Greenbay`
    }
    
    useEffect(() => {
        singlePageProduct()
    }, [productId])

    let moreItems

    if(productPage){
    moreItems = productPage.randomProducts.map(function(item){
        return <SwiperSlide key={item.id}>
        <div>
        <Link to={`/products/${item.id}`}>
        <Product
        title={item.name} 
        image={item.photoUrl} 
        purchasePrice={item.purchasePrice}/>
        </Link>
        </div>
        </SwiperSlide>
    })
    }

    if(productPage != undefined){
    return (
        <div className="single-product">

            <div className="product-informations">
                <div className="product-img">
                <Link to="/">Back to Home Page</Link>
                <h1>{productPage.name}</h1>
                <img src={productPage.photoUrl} alt={productPage.name} />
                </div>

                <div className="product-details">
                <Link to={productPage.uploader === localStorage.getItem('username') ? '/profile' : `/user/${productPage.uploader}`} className="user-link">Uploaded by: {productPage.uploader}</Link>
                <h3 className="description">Description</h3>

                <p>{productPage.description}</p>

                <h3>{productPage.expiresAt && "Auction ends at: " + productPage.expiresAt}</h3>
                <h3>{productPage.startingPrice && "Staring Price: $" + productPage.startingPrice}</h3>
                <div className="bidding-section">
                    {productPage.startingPrice && <input type="number" placeholder="Amount of bid" id="bidAmount" />}
                    {productPage.startingPrice && <button className="bidding-btn" onClick={() => props.placeBid(productId)}>Place your bid</button>}
                </div>

                <h3>Purchase Price: ${productPage.purchasePrice}</h3>
                <button className="purchase-btn" onClick={() => props.purchase(productId)}>Purchase</button>

                </div>
            </div>

            <Swiper
                className="swiper"
                spaceBetween={50}
                breakpoints={{
                    0: {
                        slidesPerView: 1
                    },
                    500: {
                        slidesPerView: 3
                    },
                    1000: {
                        slidesPerView: 4
                    }
                }}
            >
                {moreItems}
            </Swiper>

        </div>
    )
    }
}