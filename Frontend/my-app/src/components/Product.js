export default function Product(props){
    return(
        <div className="product">
            <img className="product-img" src={props.img}></img>
            <div className="product-text">
                <h1>{props.title}</h1>
                <h3>{props.price}</h3>
            </div>
        </div>
    )
}