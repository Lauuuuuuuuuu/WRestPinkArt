console.log(sessionStorage.getItem('role'))
if(sessionStorage.getItem('role')!="Artista"){
    alert("You are not an artist user, or you have not logged in")
    window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/login.html"

}
const form = document.querySelector("form")

form.onsubmit = async (e)=> {
    e.preventDefault()

    let email = sessionStorage.getItem('email')
    console.log(email)
    const formData =new FormData(form);

    try {
        let response = await fetch(`./api/users/arts/${email}`, {
            method: 'POST',
            headers: {

            },

            body: formData,


        });
        let result = await response.json();
        console.log(result);
        console.log(result.file);
        if(result.file != ""){
            window.location.href = "http://localhost:8080/WRestPinkArt-1.0-SNAPSHOT/prueba.html"
        }

    }
    catch (r) {
        console.log(r + "!error.!")
    }
}