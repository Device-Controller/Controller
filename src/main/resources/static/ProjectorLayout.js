document.getElementById('projector-layout').onclick = event => {
    var x = event.offsetX;
    var y = event.offsetY;
    drawProjector(x, y, new Date() % 360);
}

function drawProjector(x, y, rot) {
    let canvas = document.getElementById('projector-layout');
    let ctx = canvas.getContext('2d');
    let img = new Image;
    img.onload = function () {
        let width = 64;
        let w = img.naturalWidth;
        let h = img.naturalHeight;
        console.log(w);
        console.log(h);
        let ratio = w/h;
        console.log(ratio);
        let height = width/ratio;
        console.log(height);
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(rot * Math.PI / 180);
        ctx.drawImage(img, -(width / 2), -(height / 2), width, height);
        ctx.restore();
    }
    img.src = "projector.png";
}