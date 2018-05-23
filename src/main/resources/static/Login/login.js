function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    console.log(username);
    console.log(password);
    fetch('login', {
        method: "POST",
        body: JSON.stringify(new User(username, password)),
        headers: {'Content-Type': 'application/json;charset=UTF-8'}
    }).then(r => {
        if (r.ok) {
            console.log(r);
            r.text().then(t => console.log(t));
            r.json().then(e => window.location.replace(window.location.origin + e.link));
        }
    });
}

let form = document.getElementById("loginForm");
form.addEventListener("submit", e => {
    e.preventDefault();
});
fetch("/api/user/getall").then(r => {
    if (r.ok) {
        r.json().then(j => console.log(j));
    }
});

class User {
    constructor(username, password) {
        this.id = null;
        this.role = null;
        this.username = username;
        this.password = password;
        this.email = "place@hold.er";
    }
}