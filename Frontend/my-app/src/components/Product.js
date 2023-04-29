export default function Product(props){
    return(
        <div className="product">
            <div className="product-img" src={props.img}></div>
            <div className="product-text">
                <h1>{props.title}</h1>
                <h3>{props.price}</h3>
            </div>
        </div>
    )
}