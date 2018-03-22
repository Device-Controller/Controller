parseURLId(location.href);
let id;
let lampNum;

updateData();

function parseURLId(url) {
    let startIndex = url.indexOf("?") + 1;
    let endIndex = url.length + 1;
    return parseId(url.slice(startIndex, endIndex - 1));

}

function parseId(id) {
    let startIndex = id.indexOf("=") + 1;
    let endIndex = id.length + 1;
    return id.slice(startIndex, endIndex - 1);
}

function fetchProjector(id) {
    fetch('controller/getProjector?id=' + id).then(response => {
        if (response.ok) {
            this.id = id;
            response.json().then(p => console.log(p));
        }
    });
}

function mute() {
    fetch('BarkoF22/mute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

function unMute() {
    fetch('BarkoF22/unMute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

function updateData() {
    //GET CONTRAST
fetch('BarkoF22/getContrast?id=' + parseURLId(location.href)).then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("get-contrast").innerHTML = e);
    }
});

//GET POWERSTATE
fetch('BarkoF22/powerState?id=' + parseURLId(location.href)).then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("get-powerstate").innerHTML = e);
    }
});

//GET BRIGHTNESS
fetch('BarkoF22/getBrightness?id=' + parseURLId(location.href)).then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("brightness").innerHTML = e);
    }
});

//GET TEMPERATURE
fetch('BarkoF22/getThermal?id=' + parseURLId(location.href)).then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("temperature").innerHTML = e);
    }
});

//GET PROJECTOR RUNTIME
fetch('BarkoF22/getProjectorRuntime?id=' + parseURLId(location.href)).then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("projector-runtime").innerHTML = e);
    }
});

//GET LAMPRUNTIME 1
fetch('BarkoF22/lampRuntime?id=' + parseURLId(location.href) + '&lampNum=1').then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("lamp1-runtime").innerHTML = e);
    }
});

//GET LAMPSTATUS 1
fetch('BarkoF22/lampStatus?id=' + parseURLId(location.href) + '&lampNum=1').then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("lamp1-status").innerHTML = e);
    }
});

//GET LAMPRUNTIME 2
fetch('BarkoF22/lampRuntime?id=' + parseURLId(location.href) + '&lampNum=2').then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("lamp2-runtime").innerHTML = e);
    }
});

//GET LAMPSTATUS 2
fetch('BarkoF22/lampStatus?id=' + parseURLId(location.href) + '&lampNum=2').then(response => {
    if (response.ok) {
        response.json().then(e => document.getElementById("lamp2-status").innerHTML = e);
    }
});
}
