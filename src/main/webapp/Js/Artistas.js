const form = document.querySelector("form")

form.onsubmit = async (e)=> {
    e.preventDefault()

    let username = sessionStorage.getItem('username')
    console.log(username)
    const formData =new FormData(form);

    try {
        let response = await fetch(`./api/users/arts/${username}`, {
            method: 'POST',
            headers: {

            },

            body: formData,


        });
        let result = await response.json();
        console.log(result);


    }
    catch (r) {
        console.log(r + "!error.!")
    }
}