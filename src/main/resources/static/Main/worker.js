let id = -1;

function getPowerState(id) {
    fetch('MainController/powerState?id=' + id).then(response => {
        if (response.ok) {
            console.log(response);
            return response.text();
        }
        throw new Error("Failed");
    }).then(data => {
        console.log(data);
        self.postMessage(data);
    }).catch(e => console.log(e));
    setTimeOut("getPowerState()", 1000);
}

console.log("wtf");
this.onmessage = function(e) {
    console.log(e);
}