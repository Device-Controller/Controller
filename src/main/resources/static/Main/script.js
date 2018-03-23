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
        // this.worker = new Worker("worker.js");
        // this.worker.postMessage("rofl");
        //
        // this.worker.addEventListener('message', function (e) {
        //     console.log('MAIN THREAD' + e.data);
        //     document.getElementById('pro1').style.backgroundColor = e.data;
        // }, false);
    }
}
//let script = new doStuff();



function getLampStatus() {
    fetch('controller/lampStatus?lampNumber=1')
            .then(response => {
                if (response.ok) {
                    return response.text();
                }

                throw new Error("Failed");
            }).then(data => {
        console.log(data);
    })
            .catch(e => console.log("Error: " + e.message));
}
function testCheck() {

    var projectors = [];
    for (var i = 0; i < checkedList.length; i++) {
        if (checkedList[i].checked) {
            let id = i + 1;
            fetch('MainController/getProjector?id=' + id).then(response => {
                if (response.ok) {
                    response.json().then(p => console.log(p));
                }
            });

        } else {
            console.log(checkedList[i] + 'is not checked.');
        }
    }
    return projectors;
}
function powerOn() {
    for (let j = 0; j < projectors.length; j++) {
        if (checkedList[j].checked) {
            let pID = projectors[j].id;
            console.log(pID);
            fetch('MainController/powerOn?id=' + pID).then(response => {
                if (response.ok) {
                    console.log('yaey');
                    response.text().then(p => console.log(p));
                } else {
                    console.log("fuck");
                }
            });
        }
    }

}


