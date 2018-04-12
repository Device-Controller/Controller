/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let projectors = [];
var checkedList = [];
var timeout;
startUp();

function startUp() {
    fetch('test/db').then(response => {
        if (response.ok) {
            response.json().then(e => {
                projectors.push.apply(projectors, e);
                addListElements();
                updateState();
                //this.worker.postMessage("e");
            });
        }
    });
}

function powerOn() {
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            let pID = projectors[j].id;
            console.log(pID);
            fetch('MainController/powerOn?id=' + pID).then(response => {
                if (response.ok) {
                    response.text().then(p => console.log(p));
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
    let statusIcons = document.getElementsByClassName('state-icon');
    statusIcons[index].style.backgroundColor = color;
}


//this.worker.addEventListener('message', function (e) {
//    console.log('MAIN THREAD' + e.data);
//    document.getElementById('pro1').style.backgroundColor = e.data;
//}, false);
function getPowerState(id) {
    fetch('MainController/powerState?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(p => {
                switch (p) {
                    case 0:
                        powerIcon(id - 1, "#ff6600");
                        break;
                    case 1:
                        powerIcon(id - 1, "red");
                        console.log("REDDDDDDDDDD");
                        break;
                    case 2:
                        powerIcon(id - 1, "#66ff00");
                        break;
                    case 3:
                        powerIcon(id - 1, "orange");
                        break;
                    case 4:
                        powerIcon(id - 1, "yellow");
                        break;
                    case 5:
                        powerIcon(id - 1, "darkred");
                        break;
                    case 6:
                        powerIcon(id - 1, "black");
                        break;
                }
            });
        }
    });
}

// setInterval(window.setInterval(function () {
//     for (let n = 0; n < projectors.length; n++) {
//         getPowerState(n + 1);
//     }
// }
// , 1000), 5000);

function updateState() {
    for (let n = 0; n < projectors.length; n++) {
        getPowerState(n + 1);
    }
    timeout = setTimeout(updateState,2000);
}

function createGroup() {
    let group = [];
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            group.push(projectors[j]);

        }
    }
    fetch('...' + group).then(response => {
        if(response.ok) {
            return 'rolilol';
        }
        throw new Error("Failed to send message " + group);
    });
    
    console.log(group);
}

function addListElements() {
    let ul = document.getElementById("selected-list");

    for (let counter = 1; counter < projectors.length+1; counter++) {
        var li = document.createElement('li');

        li.innerHTML = "<li class='list-item' onclick='whatIsThis(this)'>"
            +"<input id='pro"+counter+"'"+" class='pro-checkbox' type='checkbox'>"
            +"<label for='pro"+counter+"'"+" class='check-label'></label>"
            +"<label for='pro"+counter+"'"+" class='text-label'>Projector "+counter+"<span class='state-icon' id='status'</label>"
            +"</li>";
        ul.appendChild(li);
    }
    document.getElementsByClassName('pro-checkbox');
}
function whatIsThis(unknownEntity) {
    console.log(unknownEntity);
}