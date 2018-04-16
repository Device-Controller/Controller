/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let projectors = [];
var checkedList = document.getElementsByClassName('pro-checkbox');
var timeout;
startUp();

function startUp() {
    fetch('test/db').then(response => {
        if (response.ok) {
            response.json().then(e => {
                projectors.push.apply(projectors, e);
                addListElements();
                updateState();
            });
        }
    });
}

function powerOn() {
    console.log("CLICKED");
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            let pID = projectors[j].id;
            fetch('MainController/powerState?id=' + pID).then(response => {
                if (response.ok) {
                    response.json().then(p => {
                        if (response.ok) {
                            console.log(p);
                            if (p === 3) {
                                fetch('MainController/powerOff?id=' + pID).then(response => {
                                    if (response.ok) {
                                        console.log(response);
                                        response.text().then(p => console.log(p));
                                    }
                                });
                            }
                            else if (p === 1 || p === 0) {
                                fetch('MainController/powerOn?id=' + pID).then(response => {
                                    if (response.ok) {
                                        console.log(response);
                                        response.text().then(p => console.log(p));
                                    }
                                });
                            }
                        }

                    });
                }
            });
        }
    }
}

function mute() {
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            let pID = projectors[j].id;
            console.log(pID);
            fetch('MainController/mute?id=' + pID).then(response => {
                if (response.ok) {
                    response.text().then(p => console.log(p));
                }
            });
        }
    }
}

function powerIcon(index, color) {
    let statusIcon = document.getElementById(index);
    statusIcon.style.backgroundColor = color;
}

function getPowerState(id) {

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
            });
        }
    });
}

function updateState() {
    for (let n = 0; n < projectors.length; n++) {
        getPowerState(projectors[n].id);
    }
    timeout = setTimeout(updateState, 2000);
}

function createGroup() {
    let group = [];
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            group.push(projectors[j]);

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

function addListElements() {
    let ul = document.getElementById("selected-list");

    for (let counter = 1; counter < projectors.length + 1; counter++) {
        var li = document.createElement('li');

        li.innerHTML =
            "<input id='pro" + counter + "'" + " class='pro-checkbox' type='checkbox'>"
            + "<label for='pro" + counter + "'" + " class='check-label'></label>"
            + "<label for='pro" + counter + "'" + " class='text-label'>Projector " + counter + "<span class='state-icon' id='"+projectors[counter-1].id+"'</label>";
        ul.appendChild(li);
    }
    document.getElementsByClassName('pro-checkbox');
}

function whatIsThis(unknownEntity) {
    console.log(checkedList);
    console.log(unknownEntity);
}
