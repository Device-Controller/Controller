let list = [];
let imgRatio = 1;

let width = 64;
let canvas = document.getElementById('device-layout');
let drawSurface = canvas.getContext('2d');
var baseDrawing = document.createElement('canvas');
let lastMouseX;
let lastMouseY;
baseDrawing.width = 500;
baseDrawing.height = 700;

function deviceViewBuild(deviceList) {
    clearAll();
    list = [];
    for (let i = 0; i < deviceList.length; i++) {
        if (isNumber(deviceList[i].xPos) && isNumber(deviceList[i].yPos) && isNumber(deviceList[i].rotation)) {
            list.push(deviceList[i]);
        }
    }
    list.forEach(p => {
        drawDevice(p);
    });
}

function isNumber(num) {
    return !isNaN(parseFloat(num)) && isFinite(num);
}

function clearAll(){
    drawSurface.clearRect(0, 0, 500, 700);
    baseDrawing.getContext("2d").clearRect(0,0,500,700);
}
function resetDrawing() {
    drawSurface.clearRect(0, 0, 500, 700);
    drawSurface.drawImage(baseDrawing, 0, 0);
}

function drawSelectorCircle(x, y) {
    drawSurface.beginPath();
    drawSurface.arc(x, y, width / 2, 0, 2 * Math.PI);
    drawSurface.stroke();
}

function checkMouseHover() {
    if (isWithinAny(lastMouseX, lastMouseY)) {
        canvas.style.cursor = "pointer";
        list.forEach(p => {
            if (isWithin(lastMouseX, lastMouseY, p.xPos, p.yPos)) {
                drawSelectorCircle(p.xPos, p.yPos);
            }
        });
    } else {
        canvas.style.cursor = "auto";
    }
}

canvas.onmousemove = event => {
    lastMouseX = event.offsetX;
    lastMouseY = event.offsetY;
    checkMouseHover();
};

canvas.onclick = event => {
    var x = event.offsetX;
    var y = event.offsetY;
    let i;
    for (i = 0; i < list.length; i++) {
        if (isWithin(x, y, list[i].xPos, list[i].yPos)) {
            window.location.href = "device?id=" + list[i].id;
        }
    }
};


function isWithinAny(x, y) {
    let changed = false;
    list.forEach(p => {
        if (isWithin(x, y, p.xPos, p.yPos)) {
            changed = true;
        }
    });
    return changed;
}

function drawDeviceCircle(p) {
    drawSelectorCircle(p.xPos, p.yPos);
}

function drawDevice(device) {
    let x = device.xPos;
    let y = device.yPos;
    let rot = device.rotation;
    let img = new Image;
    img.onload = function () {
        let w = img.naturalWidth;
        let h = img.naturalHeight;
        let ratio = w / h;
        imgRatio = ratio;
        let height = width / ratio;
        drawSurface.save();
        drawSurface.translate(x, y);
        drawSurface.rotate(rot * Math.PI / 180);
        drawSurface.drawImage(img, -(width / 2), -(height / 2), width, height);
        drawSurface.restore();
        baseDrawing.getContext('2d').drawImage(canvas, 0, 0);
    };
    img.src = "/Images/"+device.deviceInfo.deviceType.type.toLowerCase().replace(" ", "_")+".png";
}


function isWithin(x, y, xOrg, yOrg) {
    let xBound = (width) / 3.5;
    let yBound = xBound * imgRatio;
    if (Math.abs(x - xOrg) < xBound && Math.abs(y - yOrg) < yBound) {
        return true
    }
    return false;
}

let circleTimeout;
checkChecked();

function checkChecked() {
    resetDrawing();
    checkMouseHover();
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].checkbox.checked) {
            drawDeviceCircle(devices[i].device);
        }
    }
    circleTimeout = setTimeout(checkChecked, 200);
}

