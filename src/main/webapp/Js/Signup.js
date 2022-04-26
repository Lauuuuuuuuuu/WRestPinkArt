signUp.onsumbit = async (e)=> {
    e.preventDefault();

    let response = await fetch('./api/users', {
        method: 'POST',
        body: new FormData(signUp)
    });
    let result = await response.json();
    console.log(result);
};


