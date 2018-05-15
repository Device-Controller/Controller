var timeout;


getDevices().then(r => buildList(r))
    .then(setDefault)
    .catch(e => {
        console.log(e);
    });

function buildList(deviceList) {
    devices = [];
    for (let i = 0; i < deviceList.length; i++) {
        let f = deviceList[i];
        let d = new Device(f);
        addListElements(d);
    }
    updateState();
    projectorViewBuild();
}


function powerOn() {
    console.log("CLICKED POWER ON");
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].checkbox.checked) {
            let pID = devices[i].id;
            fetch('api/main/powerOn?id=' + pID).then(response => {
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
        if (devices[i].checkbox.checked) {
            let pID = devices[i].id;
            fetch('api/main/powerOff?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function mute() {
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].checkbox.checked) {
            let pID = devices[i].id;
            fetch('api/main/mute?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function unMute() {
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].checkbox.checked) {
            let pID = devices[i].id;
            fetch('api/main/unMute?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });
        }
    }
}

function powerIcon(index, color) {
    try {
        let statusIcon = devices[index].selectionBox.querySelector(".state-icon");
        let discoIcon = devices[index].selectionBox.querySelector(".disconnect-icon");
        if (color === 'none') {
            statusIcon.style.display = "none";
            discoIcon.style.display = "inline-block";
        } else {
            statusIcon.style.display = "inline-block";
            statusIcon.style.backgroundColor = color;
            discoIcon.style.display = "none";
        }
    } catch(e) {

    }
}

function getPowerState(id, n) {
    fetch('api/main/powerState?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(p => {
                switch (p) {
                    case -1:
                        powerIcon(n, 'none');
                        break;
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
        }
    }).catch(error => {
        console.log("Failed to get power state.");
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
        if (devices[j].checkbox.checked) {
            group.push(devices[j].device);
        }
    }

    let name = document.getElementById('groupname').value;
    if (name === "" || name == null || name === " " || group.length < 1) {
        alert("Please type in a group name and select one device or more.");
    } else {
        fetch("/api/devicegroup/add?groupname=" + name, {
            method: "POST",
            body: JSON.stringify(group),
            headers: {"Content-Type": "application/json"}

        }).then(response => {
            if (response.ok) {

                response.json().then(response_group => {
                    updateDropdown(true);
                    let dropdown = document.getElementById("group-select");
                });
            } else {
                throw new Error("Failed to create group: " + name);
            }
        }).catch(e => {
            console.log("Failed to create group: " + e);
        });
        document.getElementById("groupname").value = "";
    }
}

function updateCount() {
    let t = document.getElementById("check-count");
    let counter = 0;
    for (i = 0; i < devices.length; i++) {
        if (devices[i].checkbox.checked) {
            counter++;
        }
    }
    t.innerHTML = "Count: " + counter;
}

function addListElements(device) {
    let ul = document.getElementById("selected-list");
    let imageCard;
    switch (device.type) {
        case "Projector":
            imageCard = "<image src='Images/projector_icon_simple.png' alt='projector-image' class='list-icon'></image>";
            break;
        case "Sound System":
            imageCard = "<image src='Images/soundsystem_icon_simple.png' alt='soundsystem-image' class='list-icon'></image>";
            break;
    }

    var li = document.createElement('li');

    let counter = devices.length + 1;
    li.innerHTML =
        "<div class='image-card'>"
        + imageCard
        + "</div>"
        + "<input id='pro" + counter + "'" + " class='pro-checkbox' type='checkbox' onclick='updateCount()'>"
        + "<label for='pro" + counter + "'" + " class='check-label' onclick='updateCount()'></label>"
        + "<label for='pro" + counter + "'" + " class='text-label' onclick='updateCount()'>" + device.manufacturer + "<br>" + device.model + "</label>"
        + "<span class='state-icon'></span>"
        + "<span class='disconnect-icon'>"
        + "<image src='../Images/disconnect.png' alt='disconnected' class='disconnect-image'></image></span>";
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

function populateTheatre() {
    let dropdown = document.getElementById("theatre-select");
    dropdown.onchange = e => {
        document.getElementById('selected-list').innerHTML = '';

        for (let i = 0; i < devices.length; i++) {
            devices[i].checkbox.checked = false;
        }
        for (let i = 0; i < optionMap.length; i++) {
            if (optionMap[i].option.selected) {
                let optionDevices = optionMap[i].deviceGroup.devices;
                buildList(optionDevices);

            }
        }
    };
    updateTheatre();
}

function setDefault() {
    fetch("/api/theatre/getall").then(r => {
        if (r.ok) {
            r.json().then(e => {
                document.getElementById('selected-list').innerHTML = '';
                buildList(e[0].devices);
            });
        }
    })
}


function updateTheatre(index) {
    let dropdown = document.getElementById("theatre-select");
    for (let i = dropdown.children.length - 1; i > 1; i--) {
        dropdown.children[i].remove();
    }
    fetch("/api/theatre/getall").then(r => {
        if (r.ok) {
            document.getElementById('selected-list');
            r.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let x = new DeviceGroup(e[i].id, e[i].theatreName, e[i].devices);
                    let option = document.createElement("option");
                    option.text = x.groupName;
                    optionMap.push(new OptionMap(option, x));
                    dropdown.add(option, 99);
                }
                if (index) {
                    dropdown.selectedIndex = dropdown.length - 1;
                }
            });
        }
    })
}

populateTheatre();

function populateDropdown() {

    let dropdown = document.getElementById("group-select");
    dropdown.onchange = e => {
        for (let i = 0; i < devices.length; i++) {
            devices[i].checkbox.checked = false;
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
    updateDropdown();
}

function updateDropdown(index) {
    let dropdown = document.getElementById("group-select");
    for (let i = dropdown.children.length - 1; i > 1; i--) {
        dropdown.children[i].remove();
    }
    fetch("/api/devicegroup/getall").then(r => {
        if (r.ok) {
            r.json().then(e => {
                for (let i = 0; i < e.length; i++) {
                    let x = new DeviceGroup(e[i].id, e[i].groupName, e[i].devices);
                    let option = document.createElement("option");
                    option.text = x.groupName;
                    optionMap.push(new OptionMap(option, x));
                    dropdown.add(option, 99);
                }
                if (index) {
                    dropdown.selectedIndex = dropdown.length - 1;
                }
            });
        }
    })
}

populateDropdown();



