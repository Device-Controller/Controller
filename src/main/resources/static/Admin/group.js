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
    let div = document.createElement("div");
    let h5 = document.createElement("h5");
    let p = document.createElement("p");
    div.classList.add("card-body", "btn","btn-primary");
    h5.classList.add("card-title");
    p.classList.add("card-text");
    h5.innerHTML = "Add new";
    p.innerHTML = "Add new device group";
    div.appendChild(h5);
    div.appendChild(p);
    div.onclick = e => {
        fillGroupForm();
    };
    newLi.appendChild(div);
    ul.appendChild(newLi);
    for (let i = 0; i < groupEntities.length; i++) {
        let li = document.createElement("li");
        let div = document.createElement("div");
        let h5 = document.createElement("h5");
        let p = document.createElement("p");
        let p2 = document.createElement("p");
        let a = document.createElement("a");
        div.classList.add("card-body", "btn","btn-primary");
        h5.classList.add("card-title");
        p.classList.add("card-text");
        h5.innerHTML = groupEntities[i].groupName;
        p.innerHTML = "Number of devices: " + groupEntities[i].devices.length;
        p2.innerHTML = groupEntities[i].theatre.theatreName;
        a.setAttribute("list-index", "" + i);
        div.appendChild(h5);
        div.appendChild(p);
        div.appendChild(p2);
        div.appendChild(a);
        div.onclick = e => {
            fillGroupForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        li.appendChild(div);
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

function updateDevices(value,form, group) {
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
                    let checkbox = createDeviceCheckBox(j[i], "add-group-");
                    selection.appendChild(checkbox);
                }
                for (let i = 0; i < j.length; i++) {
                    let checkbox = createDeviceCheckBox(j[i], "group-");
                    selection2.appendChild(checkbox);
                }
                if (group && group.devices) {
                    for (let i = 0; i < group.devices.length; i++) {
                        let elm = document.getElementById("group-device-" + group.devices[i].id);
                        if (elm) {
                            elm.checked = true;
                        }
                    }
                }
                form.scrollIntoView();
            })
        }
    })
}

document.getElementById("addTheatre").onchange = function () {
    updateDevices(document.getElementById("addTheatre").value, document.getElementById("add-devicegroup"));
};
document.getElementById("theatre-device-group").onchange = function (group) {
    updateDevices(document.getElementById("theatre-device-group").value, document.getElementById("manage-devicegroup"), group);
};

function deleteGroup() {

    let req = new XMLHttpRequest();
    let id = document.getElementById("deviceGroupId").value;
    req.open("DELETE", "api/devicegroup/remove?id=" + id);
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = e => {
        if (req.readyState === 4 && req.status === 200) {
            showManageGroups();
            alert(req.response, 2000);
        } else if (req.readyState === 4) {
            alert(req.response, 4000);
        }
    };
    req.send();

    
}
