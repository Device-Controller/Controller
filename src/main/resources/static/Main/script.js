/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class doStuff {
    constructor() {
        //this.status = document.querySelectorAll("li");
        this.status = document.getElementById("pro1");
        console.log(status);
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
let script = new doStuff();



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

