/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var devices = [];
var devicegroups = [];
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
    let statusIcon = devices.selectionBox.
    statusIcon.style.backgroundColor = color;
}

function getPowerState(id) {
    console.log(id);

    fetch('MainController/powerState?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(p => {
                switch (p) {
                    case 0:
                        powerIcon(id, "#ff6600");
                        break;
                    case 1:
                        powerIcon(id, "red");
                        break;
                    case 2:
                        powerIcon(id, "orange");
                        break;
                    case 3:
                        powerIcon(id, "#66ff00");
                        break;
                    case 4:
                        powerIcon(id, "yellow");
                        break;
                    case 5:
                        powerIcon(id, "darkred");
                        break;
                    case 6:
                        powerIcon(id, "black");
                        break;
                }
                return true;
            });
        } else {
            return false;
        }
    });
}

function updateState() {
    console.log(devices);
    let erik = true;
    for (let n = 0; n < devices.length; n++) {
        erik = erik && getPowerState(devices[n].id);
        console.log(erik);
    }
    console.log(erik);
    if (erik) {
        timeout = setTimeout(updateState, 2000);
    }

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
        + "<label for='pro" + counter + "'" + " class='text-label'>Projector " + counter + "<span class='state-icon'</label>";
    ul.appendChild(li);
    devices.push(new DeviceMap(device, li));
    document.getElementsByClassName('pro-checkbox');
}

function whatIsThis(unknownEntity) {
    console.log(unknownEntity);
}

function getDevice(deviceId) {
    for (let i = 0; i < projectors.length; i++) {
        if (projectors[i].id == deviceId) {
            return i;
        }
    }
}

function populateDropdown() {
    let dropdown = document.getElementById("group-select");
    dropdown.onchange = e => {
        for (let i = 0; i < checkedList.length; i++) {
            checkedList[i].checked = false;
        }
        let options = dropdown.children;
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let devices = devicegroups[parseInt(options[i].id)].devices;
                for (let j = 0; j < devices.length; j++) {
                    let projectorIndex = getDevice(devices[j].id);
                    checkedList[projectorIndex].checked = true;
                }
            }
        }
    };
    fetch("devicegroup/groups").then(r => {
        if (r.ok) {
            r.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let x = new DeviceGroup(e[i].id, e[i].groupName, e[i].devices);
                    devicegroups.push(x);
                    let option = document.createElement("option");
                    option.text = x.groupName;
                    option.id = i + "";
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
    constructor(device, li, powerIcon) {
        this.id = device.id;
        this.device = device;
        this.selectionBox = li;
        this.powerIcon = powerIcon;
    }
}