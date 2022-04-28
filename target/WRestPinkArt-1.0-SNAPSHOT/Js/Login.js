const form = document.querySelector("form")

form.onsubmit= async (e) =>{
    e.preventDefault();

    let user = document.getElementById("textbox-login").value;
    console.log(user)
    try{
        let response = await fetch(`./api/users/${user}`, {
            method: 'GET',
            headers: {

                "Content-Type": "application/JSON",
            },




        });
        let result = await response.json();
        console.log(result);
        console.log()
        if(result.password == document.getElementById("passw-login").value){
            if (result.role == "Artista"){
                window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/artistas.html";
            }
            else if (result.role == "Comprador"){
                window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/prueba.html";
            }
            alert("Bienvenido: "+result.username)
        }
        else{
            alert("Contraseña Incorreta")
        }


    }catch (e) {
        console.log(e+" ERROR")
    }
}