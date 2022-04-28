const form = document.querySelector("form")

form.onsubmit= async (e) =>{
    e.preventDefault();
    alert("hola");

    let user = document.getElementById("textbox-login").value;

    let response = await fetch(`./api/users/${user}`)
    let result = await response.json();

    console.log(result);

}