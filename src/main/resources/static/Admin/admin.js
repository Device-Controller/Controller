var supportedDevices = [];

function showManageGroups() {
    hideAll();
    fetch("api/devicegroup/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                groupDisplay(j);
            })
        }
    })
}

function showManageUsers() {
    hideAll();
    document.getElementById("user-list").style.display = "block";
}


function fillTheatreForm(theatreListElement) {
    hideAll();
    if (theatreListElement) {
        document.getElementById("manage-theatre").style.display = "block";
    } else {
        document.getElementById("add-theatre").style.display = "block";
    }
}

function theatreDisplay(theatreEntities) {
    let ul = prepDisplay();
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    let newLi = document.createElement("li");
    newLi.innerHTML =
        "<div class='card-body btn btn-primary'>" +
        "<h5 class='card-title'>Add new</h5>" +
        "<p class='card-text'>Add a new theatre</p>" +
        "</div>";
    newLi.onclick = e => {
        fillTheatreForm();
    };
    ul.appendChild(newLi);
    for (let i = 0; i < theatreEntities.length; i++) {
        let li = document.createElement("li");
        li.innerHTML =
            "<div class='card-body btn btn-primary'>" +
            "<h5 class='card-title'>" + theatreEntities[i].theatreName + "</h5>" +
            "<p class='card-text'>" + theatreEntities[i].devices.length + " devices</p>" +
            "<a list-index='" + i + "'/>" +
            "</div>";
        li.onclick = e => {
            fillTheatreForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        ul.appendChild(li);
        elementList[i] = theatreEntities[i];
    }
}

function showManageTheatres() {
    hideAll();
    fetch("api/theatre/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                theatreDisplay(j);

            })
        }
    });
}

function hideAll() {
    var list = document.getElementsByClassName("manage-forms");
    for (let i = 0; i < list.length; i++) {
        list[i].style.display = "none";
    }
}


var elementList = [];

function prepDisplay() {
    let ul = document.getElementById("list");
    elementList = [];
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    return ul;
}


function initial() {
    var list = document.getElementsByClassName("management-forms");
    console.log(list);
    for (let i = 0; i < list.length; i++) {
        list[i].onsubmit = e => {
            e.preventDefault();
            let data = new FormData(list[i]);
            let req = new XMLHttpRequest();
            let urlEncodedData = "";
            let urlEncodedDataPairs = [];
            console.log(data.entries());
            for (var pair of data.entries()) {
                urlEncodedDataPairs.push(encodeURIComponent(pair[0]) + '=' + encodeURIComponent(pair[1]));
            }
            urlEncodedData = urlEncodedDataPairs.join('&').replace(/%20/g, '+');
            req.onreadystatechange = e => {
                if (req.readyState === 4 && req.status === 200) {
                    alert("Success");
                    showManageDevices();
                } else if (req.readyState === 4) {
                    alert("Could not perform action.\nError: " + req.status);
                }
            };
            req.withCredentials = true;
            req.open(list[i].getAttribute("method"), list[i].getAttribute("action"));
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send(urlEncodedData);
        };
    }
}

initial();