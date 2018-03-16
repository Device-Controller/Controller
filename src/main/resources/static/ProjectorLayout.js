let i;
let list = [];
for (i = 0; i < 12; i++) {
    list.push({
        'id': 'd', 'x': 70 + (i* 20), 'y': 70 + (((i-5.5)*3)*((i-5.5)*3)), 'rot': 270 + (i * 16.36), 'draw': function () {
            drawProjector(this.x, this.y, this.rot);
        }
    });
}
for (i = 0; i < list.length; i++) {
    list[i].draw();
}

let width = 64;

function isWithin(x, y, xOrg, yOrg) {
    let upperBound = width / 2;
    if (Math.abs(x - xOrg) < upperBound && Math.abs(y - yOrg) < upperBound) {
        return true
    }
    return false;
}

document.getElementById('projector-layout').onclick = event => {
    var x = event.offsetX;
    var y = event.offsetY;

    for (i = 0; i < list.length; i++) {
        if (isWithin(x, y, list[i].x, list[i].y)) {
            window.location.href = "http://www.google.com";
        }
    }
}

function drawProjector(x, y, rot) {
    let canvas = document.getElementById('projector-layout');
    let ctx = canvas.getContext('2d');
    let img = new Image;
    img.onload = function () {
        let w = img.naturalWidth;
        let h = img.naturalHeight;
        let ratio = w / h;
        let height = width / ratio;
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(rot * Math.PI / 180);
        ctx.drawImage(img, -(width / 2), -(height / 2), width, height);
        ctx.restore();
    }
    img.src = "projector.png";
}