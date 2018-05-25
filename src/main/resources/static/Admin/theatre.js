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

function fillTheatreDevices(form, parent, theatre, prefix) {
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
                    form.scrollIntoView();
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
    if (theatreListElement) {
        fillTheatreDevices(document.getElementById("manage-theatre"), document.getElementById("devices-in-theatre"), theatreListElement, "");
        document.getElementById("manage-theatre").style.display = "block";
        document.getElementById("theatreName").value = theatreListElement.theatreName;
        document.getElementById("theatreId").value = theatreListElement.id;
    } else {
        fillTheatreDevices(document.getElementById("add-theatre"), document.getElementById("add-devices-in-theatre"), theatreListElement, "add");
        document.getElementById("add-theatre").style.display = "block";
    }
}

function theatreDisplay(theatreEntities) {
    let ul = prepDisplay();
    let newLi = document.createElement("li");
    let div = document.createElement("div");
    let h5 = document.createElement("h5");
    let p = document.createElement("p");
    div.classList.add("card-body", "btn","btn-primary");
    h5.classList.add("card-title");
    p.classList.add("card-text");
    h5.innerHTML = "Add new";
    p.innerHTML = "Add new theatre";
    div.appendChild(h5);
    div.appendChild(p);
    div.onclick = e => {
        fillTheatreForm();
    };
    newLi.appendChild(div);
    ul.appendChild(newLi);
    for (let i = 0; i < theatreEntities.length; i++) {
        let li = document.createElement("li");
        let div = document.createElement("div");
        let h5 = document.createElement("h5");
        let p = document.createElement("p");
        let a = document.createElement("a");
        div.classList.add("card-body", "btn","btn-primary");
        h5.classList.add("card-title");
        p.classList.add("card-text");
        h5.innerHTML = theatreEntities[i].theatreName;
        p.innerHTML = theatreEntities[i].devices.length + " devices";
        a.setAttribute("list-index", "" + i);
        div.onclick = e => {
            fillTheatreForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        div.appendChild(h5);
        div.appendChild(p);
        div.appendChild(a);
        li.appendChild(div);
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
            alert(req.response, 2000);
        } else if (req.readyState === 4) {
            alert(req.response, 4000);
        }
    };
    req.send();

}