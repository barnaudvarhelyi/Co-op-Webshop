export default function Test(props){
    return(
        <div>
            <h1>Test page</h1>
            <p>Welcome, {props.formData.username}</p>
        </div>
    )
}