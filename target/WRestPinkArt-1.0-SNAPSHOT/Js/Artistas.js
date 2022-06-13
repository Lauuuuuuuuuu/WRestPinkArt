const form = document.querySelector("form")

form.onsubmit = async (e)=> {
    e.preventDefault()

    //Cambio Jp
    //let email = sessionStorage.getItem('email')
    //Cambio Jp
    let email = sessionStorage.getItem('email')

    console.log("cambié"+email)
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