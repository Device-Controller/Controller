function showManageDevices() {
    hideAll();
    fetch("api/device/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                deviceDisplay(j);

            })
        }
    });
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
        fillDeviceForm();
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

function showManageGroups() {
    hideAll();
    document.getElementById("groups-list").style.display = "block";
}

function showManageUsers() {
    hideAll();
    document.getElementById("user-list").style.display = "block";
}

function hideAll() {
    var list = document.getElementsByClassName("manage-forms");
    for (let i = 0; i < list.length; i++) {
        list[i].style.display = "none";
    }
}


fetch("/api/device/types").then(r => {
    if (r.ok) {
        r.json().then(types => {
            let dropdown = document.getElementById("addDeviceType");
            let dropdown2 = document.getElementById("deviceType");
            for (let i = 0; i < types.length; i++) {
                let option = document.createElement("option");
                let option2 = document.createElement("option");
                option.text = types[i].type;
                option2.text = types[i].type;
                dropdown.add(option, 99);
                dropdown2.add(option2, 99);
            }
        })
    }
});

function fillDeviceForm(device) {
    hideAll();
    if (device) {
        document.getElementById("manage-devices").style.display = "block";
        document.getElementById("deviceId").value = device.id;
        document.getElementById("deviceManufacturer").value = device.deviceInfo.manufacturer;
        document.getElementById("deviceModel").value = device.deviceInfo.model;
        document.getElementById("deviceType").value = device.deviceInfo.deviceType.type;
        document.getElementById("deviceIpAddress").value = device.ipAddress;
        document.getElementById("devicePort").value = device.port;
        document.getElementById("devicexPos").value = device.xPos;
        document.getElementById("deviceyPos").value = device.yPos;
        document.getElementById("deviceRotation").value = device.rotation;
    } else {
        document.getElementById("add-device").style.display = "block";
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

function deviceDisplay(deviceEntity) {
    let ul = prepDisplay();
    let newLi = document.createElement("li");
    newLi.innerHTML =
        "<div class='card-body btn btn-primary'>" +
        "<h5 class='card-title'>Add new</h5>" +
        "<p class='card-text'>Add a new device</p>" +
        "</div>";
    newLi.onclick = e => {
        fillDeviceForm();
    };
    ul.appendChild(newLi);
    for (let i = 0; i < deviceEntity.length; i++) {
        let li = document.createElement("li");
        li.innerHTML =
            "<div class='card-body btn btn-primary'>" +
            "<h5 class='card-title'>" + deviceEntity[i].deviceInfo.deviceType.type + "</h5>" +
            "<p class='card-text'>" + deviceEntity[i].deviceInfo.manufacturer + "</p>" +
            "<a list-index='" + i + "'/>" +
            "</div>";
        li.onclick = e => {
            fillDeviceForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        ul.appendChild(li);
        elementList[i] = deviceEntity[i];
    }
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

function deleteDevice() {
    let req = new XMLHttpRequest();
    let id = document.getElementById("deviceId").value;
    req.open("DELETE", "api/device/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageDevices();
            alert("Delete Success");
        } else if (req.readyState === 4) {
            alert("Could not delete device with id = " + id + ".\nError: " + req.status);
        }
    };
    req.send();

}