const form = document.querySelector("form")
let direction = null;

form.onsubmit = async (e)=> {
    e.preventDefault()
    const formData =new FormData(form);

    try {
        let response = await fetch('./api/users', {
            method: 'POST',
            headers: {

                "Content-Type": "application/x-www-form-urlencoded",
            },

            body: new URLSearchParams(formData),


        });
        let result = await response.json();
        console.log(result);
        //cambio username a email
        sessionStorage.setItem('email',document.getElementById("email").value)
        //
        sessionStorage.setItem('role',document.getElementById("roleInput").value);
        direction=window.sessionStorage.getItem('role');
        console.log(direction)
        if (direction == "Artista"){
            window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/artistas.html";
        }
        else if (direction=="Comprador"){
            window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/prueba.html";
        }

    }
    catch (r) {
        console.log(r + "!error.!")
    }
}




// if (document.getElementById("roleInput").value == "Artista"){
//    window.location.href ="http://localhost:8080/RestPinkArt-1.0-SNAPSHOT/artistas.html"
// }




