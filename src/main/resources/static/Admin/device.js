var supportedDevices = [];

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

function fillDeviceForm(device) {
    hideAll();
    if (device) {
        document.getElementById("manage-devices").style.display = "block";
        document.getElementById("deviceId").value = device.id;
        document.getElementById("deviceManufacturer").value = device.deviceInfo.manufacturer;
        document.getElementById("deviceManufacturer").onchange();
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
document.getElementById("addDeviceManufacturer").onchange = e => {
    let addMake = document.getElementById("addDeviceModel");
    while (addMake.lastChild) {
        addMake.removeChild(addMake.lastChild);
    }
    let manufacturer = document.getElementById("addDeviceManufacturer").value;
    for (let i = 0; i < supportedDevices.length; i++) {
        if (supportedDevices[i].manufacturer === manufacturer) {
            for (let k = 0; k < supportedDevices[i].models.length; k++) {
                let modelOption = document.createElement("option");
                modelOption.text = supportedDevices[i].models[k];
                addMake.add(modelOption);
            }
        }
    }
};
document.getElementById("deviceManufacturer").onchange = e => {
    let addMake = document.getElementById("deviceModel");
    while (addMake.lastChild) {
        addMake.removeChild(addMake.lastChild);
    }
    let manufacturer = document.getElementById("deviceManufacturer").value;
    for (let i = 0; i < supportedDevices.length; i++) {
        if (supportedDevices[i].manufacturer === manufacturer) {
            for (let k = 0; k < supportedDevices[i].models.length; k++) {
                let modelOption = document.createElement("option");
                modelOption.text = supportedDevices[i].models[k];
                addMake.add(modelOption);
            }
        }
    }
};


fetch("api/main/supported").then(r => {
    if (r.ok) {
        r.json().then(j => {
            console.log(j);
            let addMake = document.getElementById("addDeviceManufacturer");
            let addMake2 = document.getElementById("deviceManufacturer");
            let baseOption = document.createElement("option");
            let baseOption2 = document.createElement("option");
            baseOption.text = "--Select Manufacturer--";
            baseOption2.text = "--Select Manufacturer--";
            addMake.add(baseOption);
            addMake2.add(baseOption2);
            for (let i = 0; i < j.length; i++) {
                let addMake = document.getElementById("addDeviceManufacturer");
                let addMake2 = document.getElementById("deviceManufacturer");
                let option = document.createElement("option");
                let option2 = document.createElement("option");
                option.text = j[i].manufacturer;
                option2.text = j[i].manufacturer;
                addMake.add(option);
                addMake2.add(option2);
                supportedDevices.push(j[i]);
            }

        });
    }
});
