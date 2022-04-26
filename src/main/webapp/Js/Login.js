alert("hola");
loginForm.onsumbit = async (e) =>{
    e.preventDefault();


    let user = document.getElementById("textbox-login").value;

    let response = await fetch(`./api/users/${user}`)
    let result = await response.json();

    console.log(result);

}