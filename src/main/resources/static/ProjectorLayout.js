var myCanvas = document.getElementById('projector-layout');
var ctx = myCanvas.getContext('2d');
var tempCanvas = document.createElement('canvas')
tempCanvas.style.width = "128px";
tempCanvas.style.height = "128px";
tempCanvas.style.background = "#FFFFFF";
var tempCtx = tempCanvas.getContext('2d');
var img = new Image;
img.onload = function(){
    tempCtx.save();
    tempCtx.rotate(45*Math.PI/180);
    tempCtx.drawImage(img, 64,0,128,128);
    tempCtx.restore();
    ctx.drawImage(tempCanvas,0,0); // Or at whatever offset you like
};
img.src = "power.png";
img.style.transform = "rotate(90deg)";