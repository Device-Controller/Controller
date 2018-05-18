function showManageGroups() {
    hideAll(showManageGroups);
    fetch("api/devicegroup/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                groupDisplay(j);
            })
        }
    })
}

function groupDisplay(groupEntities) {
    let ul = prepDisplay();
    let newLi = document.createElement("li");
    newLi.innerHTML =
        "<div class='card-body btn btn-primary'>" +
        "<h5 class='card-title'>Add new</h5>" +
        "<p class='card-text'>Add a new device group</p>" +
        "</div>";
    newLi.onclick = e => {
        fillGroupForm();
    };
    ul.appendChild(newLi);
    for (let i = 0; i < groupEntities.length; i++) {
        let li = document.createElement("li");
        li.innerHTML =
            "<div class='card-body btn btn-primary'>" +
            "<h5 class='card-title'>" + groupEntities[i].groupName + "</h5>" +
            "<p class='card-text'>Number of devices: " + groupEntities[i].devices.length + "</p>" +
            // "<p class='card-text'>" + groupEntities[i].theatre.theatreName + "</p>" +
            "<a list-index='" + i + "'/>" +
            "</div>";
        li.onclick = e => {
            fillGroupForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        ul.appendChild(li);
        elementList[i] = groupEntities[i];
    }
}

function fillTheatreDropdown(select, group) {
    for (let i = select.children.length - 1; i >= 0; i--) {
        select.children[i].remove();
    }
    fetch("api/theatre/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                for (let i = 0; i < j.length; i++) {
                    let option = document.createElement("option");
                    option.text = j[i].theatreName;
                    select.add(option);
                    console.log(select);
                }
                if(group){
                    select.value = group.theatre.theatreName;
                }
                select.onchange(group);
            })
        }
    })
}
function fillGroupForm(group) {
    hideAll();
    if (group) {
        fillTheatreDropdown(document.getElementById("theatre-device-group"), group);
        document.getElementById("manage-devicegroup").style.display = "block";
        document.getElementById("deviceGroupId").value = group.id;
        document.getElementById("deviceGroupName").value = group.groupName;
    } else {
        document.getElementById("add-devicegroup").style.display = "block";
        fillTheatreDropdown(document.getElementById("addTheatre"));
    }
}

function updateDevices(value, group) {
    fetch("api/theatre/getdevices?theatrename=" + value).then(r => {
        if (r.ok) {
            r.json().then(j => {
                let selection = document.getElementById("add-device-group-devices");
                let selection2 = document.getElementById("device-group-devices");
                while (selection.lastChild) {
                    selection.removeChild(selection.lastChild);
                }
                while (selection2.lastChild) {
                    selection2.removeChild(selection2.lastChild);
                }
                for (let i = 0; i < j.length; i++) {
                    let checkbox = createDeviceCheckBox(j[i], "add");
                    selection.appendChild(checkbox);
                }
                for (let i = 0; i < j.length; i++) {
                    let checkbox = createDeviceCheckBox(j[i], "");
                    selection2.appendChild(checkbox);
                }
                if (group && group.devices) {
                    for (let i = 0; i < group.devices.length; i++) {
                        let elm = document.getElementById("device-" + group.devices[i].id);
                        if (elm) {
                            elm.checked = true;
                        }
                    }
                }
            })
        }
    })
}

document.getElementById("addTheatre").onchange = function () {
    updateDevices(document.getElementById("addTheatre").value);
};
document.getElementById("theatre-device-group").onchange = function (group) {
    updateDevices(document.getElementById("theatre-device-group").value, group);
};

function deleteGroup() {

    let req = new XMLHttpRequest();
    let id = document.getElementById("deviceGroupId").value;
    req.open("DELETE", "api/devicegroup/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageGroups();
            alert("Delete Success");
        } else if (req.readyState === 4) {
            alert("Could not delete device with id = " + id + ".\nError: " + req.status);
        }
    };
    req.send();

    
}
