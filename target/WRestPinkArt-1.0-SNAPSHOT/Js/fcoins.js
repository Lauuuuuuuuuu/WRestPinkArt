const form = document.querySelector("form")
let direction = null;

form.onsubmit = async (e)=> {
    e.preventDefault()
    const formData = new FormData(form);
    let email = document.getElementById("text-1649446318027").value;
    console.log(email);
    try {
        let response = await fetch(`./api/users/${email}`, {
            method: 'PUT',
            headers: {

                "Content-Type": "application/x-www-form-urlencoded",
            },

            body: new URLSearchParams(formData),

        });
        let result = await  response.json();
        console.log(result);
        if (result.email = email){
            alert("Your fcoins have been updated successfully, now you have "+result.coins+" fcoins.")
        }
        else{
            alert("User not founded or incorrect password")
        }
    } catch (r) {
        console.log(r + "!error.!")
    }
}