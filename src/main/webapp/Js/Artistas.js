const form = document.querySelector("form")
let direction = null;

form.onsubmit = async (e)=> {
    e.preventDefault()
    const formData =new FormData(form);
    let username = sessionStorage.getItem('username')
    try {
        let response = await fetch(`./api/users/${username}/collections`, {
            method: 'POST',
            headers: {

                "Content-Type": "application/x-www-form-urlencoded",
            },

            body: new URLSearchParams(formData),


        });
        let result = await response.json();
        console.log(result);


    }
    catch (r) {
        console.log(r + "!error.!")
    }
}