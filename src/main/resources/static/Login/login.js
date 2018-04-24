function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("pass").value;
    console.log(username);
    console.log(password);
    fetch('login/login', {
        method: "POST",
        body: JSON.stringify(new User(username, password)),
        headers: {"Content-Type": "application/json"}
    }).then(r => {
        if (r.ok) {
            r.json().then(e => window.location.replace(window.location.origin + e.link));
        }
    })
}

let form = document.getElementById("loginForm");
form.addEventListener("submit", e => {
    e.preventDefault();
});

class User {
    constructor(username, password) {
        this.id = null;
        this.role = null;
        this.username = username;
        this.password = password;
        this.email = "fuck@Off.forever";
    }
}