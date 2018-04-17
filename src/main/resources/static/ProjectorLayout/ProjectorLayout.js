let list = [];
let imgRatio = 1;

let width = 64;
let canvas = document.getElementById('projector-layout');
let drawSurface = canvas.getContext('2d');
var baseDrawing = document.createElement('canvas');
baseDrawing.width = 500;
baseDrawing.height = 700;
fetch('test/db').then(response => {
    if (response.ok) {
        response.json().then(e => {
            list.push.apply(list, e);

            list.forEach(p => {
                let x = p.xPos;
                let y = p.yPos;
                let rot = p.rotation;
                drawProjector(x, y, rot);
            });
        });
    }
});

function drawSelectorCircle(x, y) {
    list.forEach(p=>{
        if(isWithin(x,y,p.xPos,p.yPos)){
            drawSurface.beginPath();
            drawSurface.arc(p.xPos, p.yPos, width / 2, 0, 2 * Math.PI);
            drawSurface.stroke();
        }
    });
}

canvas.onmousemove = event => {
    if (isWithinAny(event.offsetX, event.offsetY)) {
        canvas.style.cursor = "pointer";
        drawSelectorCircle(event.offsetX, event.offsetY);
    } else {
        canvas.style.cursor = "auto";
        drawSurface.clearRect(0, 0, 500, 700);
        drawSurface.drawImage(baseDrawing, 0, 0);
    }
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

function drawProjector(x, y, rot) {
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
    }
    img.src = "/Images/projector.png";
}


function isWithin(x, y, xOrg, yOrg) {
    let xBound = (width) / 3.5;
    let yBound = xBound * imgRatio;
    if (Math.abs(x - xOrg) < xBound && Math.abs(y - yOrg) < yBound) {
        return true
    }
    return false;
}

