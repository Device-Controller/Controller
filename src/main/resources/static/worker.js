function getLampStatus() {
    fetch('controller/testDummy').then(response => {
        if(response.ok
)
    {
        return response.text();
    }
    throw new Error("Failed");
}).
    then(data => {
        console.log(data);
        self.postMessage(data);
}).
    catch(e => console.log(e)
);

    setTimeout("getLampStatus()", 1000);
}
/*self.onmessage = function (msg) {
    switch(msg.data.aTopic) {
        case 'do_sendWorkerMsg':
            getLampStatus(msg.data)
            break;
        default:
            throw 'rofl';
    }
}*/
self.addEventListener('message', function (e) {
    self.postMessage(e.data);
}, false);

getLampStatus();