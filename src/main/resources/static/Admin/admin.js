function showManageDevices() {
    hideAll();
    document.getElementById("manage-devices").style.display = "block";
}function showManageTheatres() {
    hideAll();
    document.getElementById("manage-theatres").style.display = "block";
}function showManageGroups() {
    hideAll();
    document.getElementById("manage-groups").style.display = "block";
}function showManageUsers() {
    hideAll();
    document.getElementById("manage-users").style.display = "block";
}

function hideAll(){
    var list = document.getElementsByClassName("manage-forms");
    for(let i = 0; i<list.length; i++){
        list[i].style.display = "none";
    }
}

function deviceDisplay(deviceEntity){

}
function initial() {
    var list = document.getElementsByTagName("form");
    for(let i = 0; i<list.length; i++){
        list[i].onsubmit(e=>{
            e.preventDefault();
            e.submit();
            return false;
        })
    }
}
initial();