/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class doStuff {
    constructor() {
        this.status = document.querySelectorAll("li");
        var i;
        for (i = 0; i < this.status.length; i++) {
            console.log(this.status[i]);
            this.status[i].onclick = event => {
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
            };

        }
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
function clickStuff() {
    getLampStatus();
}


