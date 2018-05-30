var supportedDevices = [];

function deleteDevice() {
    let req = new XMLHttpRequest();
    let id = document.getElementById("deviceId").value;
    req.open("DELETE", "api/device/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageDevices();
            alert(req.response, 2000);
        } else if (req.readyState === 4) {
            alert(req.response, 4000);
        }
    };
    req.send();

}

function deviceDisplay(deviceEntity) {
    let ul = prepDisplay();
    let divider = document.createElement("li");
    divider.innerHTML = "Create";
    divider.classList.add("card-body","btn-primary", "divider");
    let divider_bottom = document.createElement("li");
    divider_bottom.innerHTML = "Edit";
    divider_bottom.classList.add("card-body","btn-primary", "divider");
    let newLi = document.createElement("li");
    let div = document.createElement("div");
    let h5 = document.createElement("h5");
    let p = document.createElement("p");
    div.classList.add("card-body", "btn","btn-primary");
    h5.classList.add("card-title");
    p.classList.add("card-text");
    h5.innerHTML = "Add new";
    p.innerHTML = "Add new device";
    div.appendChild(h5);
    div.appendChild(p);
    div.onclick = e => {
        fillDeviceForm();
    };
    newLi.appendChild(div);
    ul.appendChild(divider);
    ul.appendChild(newLi);
    ul.appendChild(divider_bottom);
    for (let i = 0; i < deviceEntity.length; i++) {
        let li = document.createElement("li");
        let div = document.createElement("div");
        let h5 = document.createElement("h5");
        let p = document.createElement("p");
        let a = document.createElement("a");
        div.classList.add("card-body", "btn","btn-primary");
        h5.classList.add("card-title");
        h5.innerHTML = deviceEntity[i].deviceInfo.deviceType.type;
        p.classList.add("card-text");
        p.innerHTML = ((deviceEntity[i].defaultName) ? deviceEntity[i].defaultName: deviceEntity[i].deviceInfo.manufacturer);
        a.setAttribute("list-index", "" + i);
        div.appendChild(h5);
        div.appendChild(p);
        div.appendChild(a);
        div.onclick = e => {
            fillDeviceForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        li.appendChild(div);
        ul.appendChild(li);
        elementList[i] = deviceEntity[i];
    }
}

function fillDeviceForm(device) {
    hideAll();
    if (device) {
        document.getElementById("manage-devices").style.display = "block";
        document.getElementById("manage-devices").scrollIntoView();
        document.getElementById("deviceId").value = device.id;
        document.getElementById("deviceManufacturer").value = device.deviceInfo.manufacturer;
        document.getElementById("deviceManufacturer").onchange();
        document.getElementById("deviceModel").value = device.deviceInfo.model;
        document.getElementById("deviceName").value = device.defaultName;
        document.getElementById("deviceType").value = device.deviceInfo.deviceType.type;
        document.getElementById("deviceIpAddress").value = device.ipAddress;
        document.getElementById("devicePort").value = device.port;
        document.getElementById("devicexPos").value = device.xPos;
        document.getElementById("deviceyPos").value = device.yPos;
        document.getElementById("deviceRotation").value = device.rotation;
    } else {
        document.getElementById("add-device").style.display = "block";
        document.getElementById("add-device").scrollIntoView();
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
    hideAll(showManageDevices);
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
    addMake.disabled = false;
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
    addMake.disabled = false;
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
            let addMake = document.getElementById("addDeviceManufacturer");
            let addMake2 = document.getElementById("deviceManufacturer");
            let baseOption = document.createElement("option");
            let baseOption2 = document.createElement("option");
            baseOption.text = "--Select Manufacturer--";
            baseOption.style.display = "none";
            baseOption2.style.display = "none";
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
