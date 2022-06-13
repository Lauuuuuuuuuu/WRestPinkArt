const form = document.querySelector("form")

form.onsubmit= async (e) =>{
    e.preventDefault();


    let email = document.getElementById("emailbox-login").value;
    sessionStorage.setItem('email',email);
    console.log(sessionStorage.getItem('email'));
    try{
        let response = await fetch(`./api/users/${email}`, {
            method: 'GET',
            headers: {

                "Content-Type": "application/JSON",
            },




        });
        let result = await response.json();
        console.log(result);
        sessionStorage.setItem('role',result.role);
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
            alert("usuario no encontrado o contraseña incorrecta.")
        }


    }catch (e) {
        console.log(e+" ERROR")
    }
}