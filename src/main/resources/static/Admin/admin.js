var elementList = [];
var lastForm;
var alertText = document.getElementById("alert");
function hideAll(form) {
    var list = document.getElementsByClassName("manage-forms");
    if (form) {
        lastForm = form;
    }
    for (let i = 0; i < list.length; i++) {
        list[i].style.display = "none";
    }
}

function prepDisplay() {
    let ul = document.getElementById("list");
    elementList = [];
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    return ul;
}


function initial() {
    var list = document.getElementsByClassName("management-forms");
    for (let i = 0; i < list.length; i++) {
        list[i].onsubmit = e => {
            e.preventDefault();
            let data = new FormData(list[i]);
            let req = new XMLHttpRequest();
            let urlEncodedData = "";
            let urlEncodedDataPairs = [];
            for (var pair of data.entries()) {
                urlEncodedDataPairs.push(encodeURIComponent(pair[0]) + '=' + encodeURIComponent(pair[1]));
            }
            urlEncodedData = urlEncodedDataPairs.join('&').replace(/%20/g, '+');
            req.onreadystatechange = e => {
                if (req.readyState === 4 && req.status === 200) {
                    alert(req.response, 2000);
                    updateList();
                } else if (req.readyState === 4) {
                    alert(req.response, 4000);
                }
            };
            req.withCredentials = true;
            req.open(list[i].getAttribute("method"), list[i].getAttribute("action"));
            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            req.send(urlEncodedData);
        };
    }
}

initial();
function updateList() {
    if(lastForm){
        lastForm();
    }
}
var alertTimeout = 4000;
alert = function (text, time) {
    alertText.innerHTML = text;
    alertText.onchange();
    alertTimeout = time;

};

function alertShow(){
    let alert = document.getElementById("alert");
    alert.style.display = "fixed";
    function clearAlert() {
        alert.innerHTML = "";
        alert.style.display= "none";
    }
    let timeout = setTimeout(clearAlert, alertTimeout);
}