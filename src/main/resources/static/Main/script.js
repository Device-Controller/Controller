/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var devices = [];
var optionMap = [];
var timeout;
startUp();

function startUp() {
    fetch('test/db').then(response => {
        if (response.ok) {
            response.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let f = e[i];
                    let d = new Device(f);
                    addListElements(d);
                }
                updateState();
            });
        }
    });
}

function powerOn() {
    console.log("CLICKED POWER ON");
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].selectionBox.checked) {
            let pID = devices[i].id;
            fetch('MainController/powerOn?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function powerOff() {
    console.log("CLICKED POWER OFF");
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].selectionBox.checked) {
            let pID = devices[i].id;
            fetch('MainController/powerOff?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function mute() {
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].selectionBox.checked) {
            let pID = devices[i].id;
            fetch('MainController/mute?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function unMute() {
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].selectionBox.checked) {
            let pID = devices[i].id;
            fetch('MainController/unMute?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function powerIcon(index, color) {
    let statusIcon = devices[index].selectionBox.querySelector(".state-icon");
    console.log(devices[index].selectionBox);
    statusIcon.style.backgroundColor = color;
}

function getPowerState(id, n) {
    fetch('MainController/powerState?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(p => {
                switch (p) {
                    case 0:
                        powerIcon(n, "#ff6600");
                        break;
                    case 1:
                        powerIcon(n, "red");
                        break;
                    case 2:
                        powerIcon(n, "orange");
                        break;
                    case 3:
                        powerIcon(n, "#66ff00");
                        break;
                    case 4:
                        powerIcon(n, "yellow");
                        break;
                    case 5:
                        powerIcon(n, "darkred");
                        break;
                    case 6:
                        powerIcon(n, "black");
                        break;
                }
            });
        } else {
        }
    });
}

function updateState() {
    for (let n = 0; n < devices.length; n++) {
        getPowerState(devices[n].id, n);
    }
    timeout = setTimeout(updateState, 2000);

}

function createGroup() {
    let group = [];
    for (let j = 0; j < devices.length; j++) {
        if (devices[j].selectionBox.checked) {
            group.push(devices[j]);

        }
    }
    fetch('...' + group).then(response => {
        if (response.ok) {
            return 'rolilol';
        }
        throw new Error("Failed to send message " + group);
    });

    console.log(group);
}

function addListElements(device) {
    let ul = document.getElementById("selected-list");


    var li = document.createElement('li');

    let counter = devices.length + 1;
    li.innerHTML =
        "<input id='pro" + counter + "'" + " class='pro-checkbox' type='checkbox'>"
        + "<label for='pro" + counter + "'" + " class='check-label'></label>"
        + "<span class='state-icon'></span>"
        + "<label for='pro" + counter + "'" + " class='text-label'>Projector " + counter + "</label>";
    ul.appendChild(li);
    devices.push(new DeviceMap(device, li, document.getElementById("pro" + counter)));

    document.getElementsByClassName('pro-checkbox');
}

function whatIsThis(unknownEntity) {
    console.log(unknownEntity);
}

function getDeviceSelectionBox(id) {
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].device.id == parseInt(id)) {
            return devices[i].checkbox;
        }
    }
    return "";
}

function populateDropdown() {
    let dropdown = document.getElementById("group-select");
    dropdown.onchange = e => {
        for (let i = 0; i < devices.length; i++) {
            devices[i].selectionBox.checked = false;
        }
        for (let i = 0; i < optionMap.length; i++) {
            if (optionMap[i].option.selected) {
                let optionDevices = optionMap[i].deviceGroup.devices;
                for (let j = 0; j < optionDevices.length; j++) {
                    let vara = getDeviceSelectionBox(optionDevices[j].id);
                    vara.checked = true;
                }
            }
        }
    };
    fetch("devicegroup/groups").then(r => {
        if (r.ok) {
            r.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let x = new DeviceGroup(e[i].id, e[i].groupName, e[i].devices);
                    let option = document.createElement("option");
                    option.text = x.groupName;
                    optionMap.push(new OptionMap(option, x));
                    dropdown.add(option, 99);
                }
            })
        }
    })
}

populateDropdown();

class DeviceGroup {
    constructor(id, groupName, devices) {
        this.id = id;
        this.groupName = groupName;
        this.devices = devices;
    }
}

class Device {
    constructor(jsonObject) {
        this.ipAddress = jsonObject.ipAddress;
        this.port = jsonObject.port;
        this.xPos = jsonObject.xPos;
        this.yPos = jsonObject.yPos;
        this.rotation = jsonObject.rotation;
        this.id = jsonObject.id;
    }
}

class DeviceMap {
    constructor(device, li, checkbox) {
        this.id = device.id;
        this.device = device;
        this.selectionBox = li;
        this.checkbox = checkbox;
    }
}

class OptionMap {
    constructor(option, deviceGroup) {
        this.option = option;
        this.deviceGroup = deviceGroup;
    }
}