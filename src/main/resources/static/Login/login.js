function login(){
    let username = document.getElementById("username").value;
    let password = document.getElementById("pass").value;
    console.log(username);
    console.log(password);
    fetch('login/login?username=' + username + '&password=' + password).then(r=>{
        if(r.ok){
            r.json().then(e => console.log(e));
        }
    })
}

let form = document.getElementById("loginForm");
form.addEventListener("submit", e=>{
    e.preventDefault();
})