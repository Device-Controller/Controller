/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class doStuff {
    constructor() {
        this.status = document.querySelector("#neger");

        this.status.onclick = event => {
            console.log("clicked")
            fetch('controller/lampStatus?lampNumber=1')
                    .then(response => {
                        if (response.ok) {
                            console.log(response.body);
                            return response.body();
                        }

                        throw new Error("Failed");
                    });
        };

    }
}
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


