


function showManageGroups() {
    hideAll();
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

function fillGroupForm(group) {
    hideAll();
    if (group) {
        document.getElementById("manage-devicegroup").style.display = "block";
        document.getElementById("deviceGroupId").value = group.id;
        document.getElementById("deviceGroupName").value = group.groupName;
    } else {
        document.getElementById("add-devicegroup").style.display = "block";
    }
}
