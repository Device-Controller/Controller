
function showManageTheatres() {
    hideAll();
    fetch("api/theatre/getall").then(r => {
        if (r.ok) {
            r.json().then(j => {
                theatreDisplay(j);
            })
        }
    });
}


function createDeviceCheckBox(device) {
    let checkbox = document.createElement("input");
    let div = document.createElement("div");
    let label = document.createElement("label");
    checkbox.type = "checkbox";
    checkbox.setAttribute("id", "deviceCheckBox" + device.id);
    checkbox.setAttribute("name", "deviceCheckBox" + device.id);
    label.setAttribute("for", "deviceCheckBox" +device.id);
    label.innerHTML = device.defaultName +"," + device.deviceInfo.manufacturer + " " + device.deviceInfo.model;
    div.appendChild(checkbox);
    div.appendChild(label);
    return div;

}

function fillTheatreDevices(parent) {
    fetch("/api/device/getall").then(r=>{
        if(r.ok){
            r.json().then(j=>{
                for(let i = 0; i< j.length; i++){
                    let checkbox = createDeviceCheckBox(j[i]);
                    parent.appendChild(checkbox);
                }
            })
        }
    })
}

function fillTheatreForm(theatreListElement) {
    hideAll();
    if (theatreListElement) {
        document.getElementById("manage-theatre").style.display = "block";
    } else {
        document.getElementById("add-theatre").style.display = "block";
        fillTheatreDevices(document.getElementById("add-devices-in-theatre"));
    }
}

function theatreDisplay(theatreEntities) {
    let ul = prepDisplay();
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    let newLi = document.createElement("li");
    newLi.innerHTML =
        "<div class='card-body btn btn-primary'>" +
        "<h5 class='card-title'>Add new</h5>" +
        "<p class='card-text'>Add a new theatre</p>" +
        "</div>";
    newLi.onclick = e => {
        fillTheatreForm();
    };
    ul.appendChild(newLi);
    for (let i = 0; i < theatreEntities.length; i++) {
        let li = document.createElement("li");
        li.innerHTML =
            "<div class='card-body btn btn-primary'>" +
            "<h5 class='card-title'>" + theatreEntities[i].theatreName + "</h5>" +
            "<p class='card-text'>" + theatreEntities[i].devices.length + " devices</p>" +
            "<a list-index='" + i + "'/>" +
            "</div>";
        li.onclick = e => {
            fillTheatreForm(elementList[li.getElementsByTagName("a")[0].getAttribute("list-index")]);
        };
        ul.appendChild(li);
        elementList[i] = theatreEntities[i];
    }
}
