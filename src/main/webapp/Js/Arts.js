
let container = document.getElementById("container");
function loadData(){
    fetch("api/users/obtenerobras")
        .then(response => response.json())
        .then(jsondata =>{
            jsondata.map(async register => {


                let obra = document.createElement("div");
                obra.style = "max-width:400px;background-color: #e7e0e0bd;border-radius: 5px;border-style: groove;"
                    + " margin-top: 100px;margin-right: auto; margin-left: auto;margin-bottom: 100px;min-width: 800px;"
                    + "min-height: 500px";
                let image = document.createElement("img");
                image.src = register["file"];
                image.width = 400;
                image.style = "border-radius: 5px;border-style: groove; margin-left: 200px;margin-top: 30px;margin-bottom: 30px;  border-color: #d8b1b1";


                let title = document.createElement("p");
                title.innerHTML = "Titulo: " + register["title"]
                title.style = "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;  font-size: 20px;"

                let creator = document.createElement("p");
                creator.innerHTML = "Creador: " + register["author"];
                creator.style = "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;  font-size: 20px;"

                let price = document.createElement("p");
                price.innerHTML = "Precio: " + register["price"] + " Fcoins";
                price.style = "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;  font-size: 20px;"

                let collection = document.createElement("p");
                collection.innerHTML = "Colección: " + register["collection"];
                collection.style = "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;  font-size: 20px;"

                let likes = document.createElement("button")
                likes.id = `btnLike_${register["file"]}`;
                likes.innerHTML = "Likes: ";
                likes.style = "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;q  font-size: 20px;"

                /*    likes.document.ready(function(){
                        $("likes").click(function(){
                            if( $(this).val() == 0 ){
                                $(this).val(1);
                            }else{
                                $(this).val(0);
                            }
                        })
                    })*/

                // String id_art = register["id_art"];
                let cantidad_likes = document.createElement("input")

                let imagepath = register["file"].split("\\")[1];

                //console.log(StringRandom)

                const cantidad = await fetch(`./api/users/arts/${imagepath}/likes`).then(response => response.json());

                console.log(cantidad)

                cantidad_likes.id = `input_${register["file"]}`;
                cantidad_likes.disabled = false;
                cantidad_likes.value = cantidad;
                //register["likes"]
                likes.addEventListener('click',async function () {

                    let username = sessionStorage.getItem("email");

                    let agregar = await fetch(`./api/users/${username}/arts/${imagepath}/likes/like`, {
                        method: "POST",
                    }).then(response => response.json());

                    agregar = Number(agregar);
                    cantidad_likes.value = Number(cantidad_likes.value) + agregar;

                });

                obra.appendChild(image);
                obra.appendChild(title);
                obra.appendChild(creator);
                obra.appendChild(price);
                obra.appendChild(collection);
                obra.appendChild(likes);
                obra.appendChild(cantidad_likes);
                container.appendChild(obra);

                //document.getElementById(`btnLike_${register["file"]}`).click(darlike());
                let artPrice = document.createElement("input");
                artPrice.type = "hidden";
                artPrice.name = "price"
                artPrice.value =register["price"];

                let userBuyer = document.createElement("input");
                userBuyer.type = "hidden";
                userBuyer.name = "userBuyer";
                userBuyer.value = sessionStorage.getItem('email');

                let userSeller = document.createElement("input");
                userSeller.type = "hidden";
                userSeller.name = "userSeller";
                userSeller.value = register["author"];

                let artId = document.createElement("input");
                artId.type = "hidden";
                artId.name = "artId";
                artId.value = register["id_art"];

                let submit = document.createElement("button");
                submit.type = "submit";
                submit.innerHTML = "Comprar";
                submit.style = "width:80px; height:40px;";

                let comprar = document.createElement("form");
                comprar.appendChild(artPrice);
                comprar.appendChild(userBuyer);
                comprar.appendChild(userSeller);
                comprar.appendChild(artId);
                comprar.appendChild(submit);

                comprar.onsubmit = async (e)=>{
                    e.preventDefault();
                    const formData = new FormData(comprar);

                    let response = await fetch('./api/wallet/buy', {
                        method: 'PUT',
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded",
                        },
                        body: new URLSearchParams(formData),
                    });

                    if(response.ok){
                        alert('Compra realizada con exito');
                    }else {
                        alert('Saldo Insuficiente para comprar esta obra');
                    }
                }

                let formdiv = document.createElement("div");
                formdiv.appendChild(comprar);
                obra.appendChild(formdiv);

                container.appendChild(obra);


            })
        })
}

window.addEventListener("DOMContentLoaded",this.loadData);