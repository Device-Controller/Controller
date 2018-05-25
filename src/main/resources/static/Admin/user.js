
function fillUserForm(userListElement) {
    hideAll();
    if (userListElement) {
        document.getElementById("manage-user").style.display = "block";
        document.getElementById("manage-user").scrollIntoView();
        document.getElementById("userId").value = userListElement.id;
        document.getElementById("username").value = userListElement.username;
        document.getElementById("email").value = userListElement.email;
        populateRoles(document.getElementById("role"), userListElement.role.roleName);
    } else {
        document.getElementById("add-user").style.display = "block";
        document.getElementById("add-user").scrollIntoView();
        populateRoles(document.getElementById("addRole"));
    }
}

function userDisplay(userEntities) {
    let ul = prepDisplay();
    let newLi = document.createElement("li");
    let div = document.createElement("div");
    let h5 = document.createElement("h5");
    let p = document.createElement("p");
    div.classList.add("card-body", "btn","btn-primary");
    h5.classList.add("card-title");
    p.classList.add("card-text");
    h5.innerHTML = "Add new";
    p.innerHTML = "Add new user";
    div.appendChild(h5);
    div.appendChild(p);
    div.onclick = e => {
        fillUserForm();
    };
    newLi.appendChild(div);
    ul.appendChild(newLi);
    for (let i = 0; i < userEntities.length; i++) {
        let li = document.createElement("li");
        let div = document.createElement("div");
        let h5 = document.createElement("h5");
        let p = document.createElement("p");
        let a = document.createElement("a");
        div.classList.add("card-body", "btn","btn-primary");
        h5.classList.add("card-title");
        p.classList.add("card-text");
        h5.innerHTML = userEntities[i].username;
        p.innerHTML = userEntities[i].role.roleName.substring(0,1) + userEntities[i].role.roleName.substring(1).toLowerCase();
        a.setAttribute("list-index", "" + i);
        div.appendChild(h5);
        div.appendChild(p);
        div.appendChild(a);
        div.onclick = e => {
            fillUserForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        li.appendChild(div);
        ul.appendChild(li);
        elementList[i] = userEntities[i];
    }
}

function showManageUsers() {
    hideAll(showManageUsers);
    fetch("api/user/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                userDisplay(j);
            })
        }
    })
}

function populateRoles(dropdown, value){
    for (let i = dropdown.children.length - 1; i >= 0; i--) {
        dropdown.children[i].remove();
    }
    fetch("api/user/getroles").then(r=>{
        if(r.ok){
            r.json().then(j=>{
                for(let i = 0; i < j.length; i++){
                    let option = document.createElement("option");
                    option.text = j[i].roleName;
                    dropdown.add(option);
                }
                if(value){
                    dropdown.value = value;
                }
            })
        }
    })
}
function addCheck(input) {
    if (input.value !== document.getElementById('addPassword').value) {
        input.setCustomValidity('Password Must be Matching.');
    } else {
        // input is valid -- reset the error message
        input.setCustomValidity('');
    }
}
function check(input) {
    if (input.value !== document.getElementById('password').value) {
        input.setCustomValidity('Password Must be Matching.');
    } else {
        input.setCustomValidity('');
    }
}

function deleteUser(){
    let req = new XMLHttpRequest();
    let id = document.getElementById("userId").value;
    req.open("DELETE", "api/user/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageUsers();
            alert(req.response, 2000);
        } else if (req.readyState === 4) {
            alert(req.response, 4000)
        }
    };
    req.send();
}