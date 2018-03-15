document.getElementById('projector-layout').onclick = event => {
    var x = event.offsetX;
    var y = event.offsetY;
    drawProjector(x,y,new Date() % 360);
}

function drawProjector(x, y, rot) {
    let myCanvas = document.getElementById('projector-layout');
    let ctx = myCanvas.getContext('2d');
    let img = new Image;
    let width = 64;
    let height = 64;
    img.onload = function () {
        ctx.save();
        ctx.translate(x,y);
        ctx.rotate(rot*Math.PI/180);
        ctx.drawImage(img, -(width/2), -(height/2), width, height);
        ctx.restore();
    }
    img.src = "projector-icon.png";
}