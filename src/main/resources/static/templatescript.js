/*
 * includeHTML code is borrowed from https://www.w3schools.com/howto/howto_html_include.asp 
 */

var devices = [];
var optionMap = [];

let orgFetch = fetch;
fetch = function (dest) {
    return orgFetch(dest, {
        method: 'GET',
        credentials: 'include'
    });
};

function getDevices() {
    devices = [];
    return fetch('test/db', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then((response) => response.json())
};

function includeHTML() {
    var z, i, elmnt, file, xhttp;
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
        elmnt = z[i];
        file = elmnt.getAttribute("include-html");
        if (file) {
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        elmnt.innerHTML = this.responseText;
                    }
                    if (this.status == 404) {
                        elmnt.innerHTML = "Page not found.";
                    }
                    elmnt.removeAttribute("include-html");
                    includeHTML();
                }
            }
            xhttp.open("GET", file, true);
            xhttp.send();
            return;
        }
    }
}

function dropDownFunction() {
    var x = document.getElementById("navMob");
    if (x.className === "nav-mob") {
        x.className += " responsive";
    } else {
        x.className = "nav-mob";
    }
}

function handleRefused(error) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}

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
        this.type = jsonObject.deviceInfo.deviceType.type;
        this.manufacturer = jsonObject.deviceInfo.manufacturer;
        this.model = jsonObject.deviceInfo.model;
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