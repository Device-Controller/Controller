document.getElementById("addRoleForm").addEventListener("submit", e=>{
    e.preventDefault();
    let roleName = document.getElementById("roleTitle").value;
    let addDevice = document.getElementById("addDevicePrivilege").checked;
    let removeDevice = document.getElementById("removeDevicePrivilege").checked;
    let addUser = document.getElementById("addUserPrivilege").checked;
    let removeUser = document.getElementById("removeUserPrivilege").checked;
    console.log(JSON.stringify(new Role(roleName, addDevice, removeDevice, addUser, removeUser)));
    fetch('/test/role', {
        method: 'POST',
        body: JSON.stringify(new Role(roleName, addDevice, removeDevice, addUser, removeUser)),
        headers: {'Content-Type': 'application/json; charset=UTF-8'}
    }).then(r=>{
        if(r.ok){
            r.json().then(e=>console.log(e));
        }
    })
});

function hideAll() {
    let list = document.getElementsByClassName("adding");
    for(var i = 0; i<list.length; i++) {
        list[i].style.display = "none";
    }
}

function showAddRole(){
    hideAll();
    document.getElementById("add-role").style.display = "inline";
}
function showAddUser(){
    hideAll();
    document.getElementById("add-user").style.display = "inline";
}
function showAddDeviceGroup(){
    hideAll();
    document.getElementById("add-device-group").style.display = "inline";
}
function showAddDevice(){
    hideAll();
    document.getElementById("add-device").style.display = "inline";
}
function showAddDeviceType(){
    hideAll();
    document.getElementById("add-device-type").style.display = "inline";
}

fetch('test/roles').then(result => {
    if(result.ok){
        result.json().then(e=> {
                console.log(e);
        });
    }
});

class Role {
    constructor(roleName, addDevice, removeDevice, addUser, removeUser){
        this.roleName = roleName;
        this.addDevice = addDevice;
        this.removeDevice = removeDevice;
        this.createUser = addUser;
        this.removeUser = removeUser;
    }
}