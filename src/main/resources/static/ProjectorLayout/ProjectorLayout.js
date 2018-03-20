let list = [];
let imgRatio = 1;
fetch('controller/db').then(response => {
    if (response.ok) {
        response.json().then(e => {
            list.push.apply(list, e);

            list.forEach(p => {
                let x = p.x;
                let y = p.y;
                let rot = p.rotDeg;
                drawProjector(x, y, rot);
            });
        });
    }

});

document.getElementById('projector-layout').onclick = event => {
    var x = event.offsetX;
    var y = event.offsetY;
    let i;
    for (i = 0; i < list.length; i++) {
        if (isWithin(x, y, list[i].x, list[i].y)) {
            window.location.href = "projector?id=" + list[i].id;
        }
    }
};
function drawProjector(x, y, rot) {
    let canvas = document.getElementById('projector-layout');
    let ctx = canvas.getContext('2d');
    let img = new Image;
    img.onload = function () {
        let w = img.naturalWidth;
        let h = img.naturalHeight;
        let ratio = w / h;
        imgRatio = ratio;
        let height = width / ratio;
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(rot * Math.PI / 180);
        ctx.drawImage(img, -(width / 2), -(height / 2), width, height);
        ctx.restore();
    }
    img.src = "/../Images/projector.png";
}

let width = 64;

function isWithin(x, y, xOrg, yOrg) {
    let xBound = (width) / 3.5;
    let yBound = xBound * imgRatio;
    if (Math.abs(x - xOrg) < xBound && Math.abs(y - yOrg) < yBound) {
        return true
    }
    return false;
}

