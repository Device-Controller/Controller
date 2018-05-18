
function fillUserForm(userListElement) {
    hideAll();
    if (userListElement) {
        document.getElementById("manage-user").style.display = "block";
    } else {
        document.getElementById("add-user").style.display = "block";
    }
}

function userDisplay(userEntities) {
    let ul = prepDisplay();
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    let newLi = document.createElement("li");
    newLi.innerHTML =
        "<div class='card-body btn btn-primary'>" +
        "<h5 class='card-title'>Add new</h5>" +
        "<p class='card-text'>Add a new user</p>" +
        "</div>";
    newLi.onclick = e => {
        fillUserForm();
    };
    ul.appendChild(newLi);
    for (let i = 0; i < userEntities.length; i++) {
        let li = document.createElement("li");
        li.innerHTML =
            "<div class='card-body btn btn-primary'>" +
            "<h5 class='card-title'>" + userEntities[i].username + "</h5>" +
            "<p class='card-text'>" + userEntities[i].role.roleName + " roles</p>" +
            "<a list-index='" + i + "'/>" +
            "</div>";
        li.onclick = e => {
            fillUserForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
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