const form = document.querySelector("form")
let direction = null;

form.onsubmit = async (e)=> {
    e.preventDefault()
    const formData = new FormData(form);
    let user = document.getElementById("text-1649446318027").value;

    try {
        let response = await fetch(`./api/users/${user}`, {
            method: 'PUT',
            headers: {

                "Content-Type": "application/x-www-form-urlencoded",
            },

            body: new URLSearchParams(formData),

        });
        let result = await  response.json();
        console.log(result);
        if (result.username == user){
            alert("Your fcoins have been updated successfully, now you have "+result.coins+" fcoins.")
        }
        else{
            alert("User not founded or incorrect password")
        }
    } catch (r) {
        console.log(r + "!error.!")
    }
}