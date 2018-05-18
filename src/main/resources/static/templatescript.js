/*
 * includeHTML code is borrowed from https://www.w3schools.com/howto/howto_html_include.asp 
 */

var devices = [];
var optionMap = [];

let orgFetch = fetch;
fetch = function (dest, obj) {
    if(!obj) {
        return orgFetch(dest, {
            method: 'GET',
            credentials: 'include'
        });
    }
    obj.credentials = 'include';
    return orgFetch(dest, obj);
};

function getDevices() {
    devices = [];
    return fetch('api/device/getall', {
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

function displayRole() {
    fetch("api/user/getcurrent").then(response => {
        if (response.ok) {
            response.json().then(e => {
                var user = new User(e);
                if (user.roleName === "ADMIN") {
                    document.getElementById("admin-link").style.display = "block";
                } else {
                    document.getElementById("admin-link").style.display = "none";
                }
            })
        }
    });

}
displayRole();
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

class User {
    constructor(jsonObject) {
        this.id = jsonObject.id;
        this.role = jsonObject.role;
        this.roleName = jsonObject.role.roleName;
        this.username = jsonObject.username;
        this.email = jsonObject.email;
    }
}

class Theatre {
    constructor(jsonObject) {
        this.id = jsonObject.id;
        this.theatreName = jsonObject.theatreName;
        this.devices = jsonObject.devices;
        this.deviceGroups = jsonObject.deviceGroups;

    }
}