/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let projectors = [];
var checkedList = document.getElementsByClassName('pro-checkbox');
fetch('test/db').then(response => {
    if (response.ok) {
        response.json().then(e => {
            projectors.push.apply(projectors, e);
            //this.worker.postMessage("e");
        });
    }
});

class doStuff {
    constructor() {
        //this.status = document.querySelectorAll("li");
        this.status = document.getElementById("pro1");
        var i;
        //for (i = 0; i < this.status.length; i++) {
        this.status.onclick = event => {
            fetch('controller/lampStatus?lampNumber=1')
                    .then(response => {
                        if (response.ok)
                            return response.text();
                        {
                        }

                        throw new Error("Failed");
                    }).then(data => {
                console.log(data);
            })
                    .catch(e => console.log("Error: " + e.message));
        };
        //}
    }
}
//let script = new doStuff();


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

setInterval(window.setInterval(function () {
    for (let n = 0; n < projectors.length; n++) {
        getPowerState(n + 1);
    }
}
, 1000), 5000);


