function showManageTheatres() {
    hideAll(showManageTheatres);
    fetch("api/theatre/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                theatreDisplay(j);
            })
        }
    });
}


function createDeviceCheckBox(device, prefix) {
    let container = document.createElement("label");
    let checkbox = document.createElement("input");
    let div = document.createElement("div");
    let label = document.createElement("label");
    let checkmark = document.createElement("span");
    div.setAttribute("class", "cb-background");
    container.setAttribute("class", "container-cb");
    checkbox.type = "checkbox";
    checkbox.setAttribute("id", prefix + "device-" + device.id);
    checkbox.setAttribute("name", "device-id");
    checkbox.setAttribute("value", device.id);
    label.setAttribute("for", prefix + "device-" + device.id);
    label.setAttribute("class", "cb-text");
    checkmark.setAttribute("class", "checkmark");
    if (device.defaultName) {
        label.innerHTML = device.defaultName
    } else {
        label.innerHTML = device.deviceInfo.manufacturer + " " + device.deviceInfo.model + ", " + device.ipAddress;
    }
    label.innerHTML += "<br>"+device.deviceInfo.deviceType.type;
    container.appendChild(checkbox);
    container.appendChild(label);
    container.appendChild(checkmark);
    div.appendChild(container);
    return div;
}

function fillTheatreDevices(parent, theatre, prefix) {
    fetch("/api/device/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                    for (let i = 0; i < j.length; i++) {
                        let checkbox = createDeviceCheckBox(j[i], prefix);
                        parent.appendChild(checkbox);
                    }
                    if (theatre) {
                        for (let i = 0; i < theatre.devices.length; i++) {
                            let elm = document.getElementById("device-" + theatre.devices[i].id);
                            if (elm) {
                                elm.checked = true;
                            }
                        }
                    }
                }
            )
        }
    })
}

function clearDevices() {
    let add = document.getElementById("add-devices-in-theatre");
    while (add.lastChild) {
        add.removeChild(add.lastChild);
    }
    let manage = document.getElementById("devices-in-theatre");
    while (manage.lastChild) {
        manage.removeChild(manage.lastChild);
    }
}

function fillTheatreForm(theatreListElement) {
    hideAll();
    clearDevices();
    fillTheatreDevices(document.getElementById("add-devices-in-theatre"), theatreListElement, "add");
    fillTheatreDevices(document.getElementById("devices-in-theatre"), theatreListElement, "");
    if (theatreListElement) {
        document.getElementById("manage-theatre").style.display = "block";
        document.getElementById("theatreName").value = theatreListElement.theatreName;
        document.getElementById("theatreId").value = theatreListElement.id;
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

function deleteTheatre() {
    let req = new XMLHttpRequest();
    let id = document.getElementById("theatreId").value;
    req.open("DELETE", "api/theatre/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageTheatres();
            alert("Delete Success");
        } else if (req.readyState === 4) {
            alert("Could not delete device with id = " + id + ".\nError: " + req.status);
        }
    };
    req.send();

}