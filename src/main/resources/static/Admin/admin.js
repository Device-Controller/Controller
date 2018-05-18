var elementList = [];
var lastForm;

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
    console.log(list);
    for (let i = 0; i < list.length; i++) {
        list[i].onsubmit = e => {
            e.preventDefault();
            let data = new FormData(list[i]);
            let req = new XMLHttpRequest();
            let urlEncodedData = "";
            let urlEncodedDataPairs = [];
            console.log(data.entries());
            for (var pair of data.entries()) {
                urlEncodedDataPairs.push(encodeURIComponent(pair[0]) + '=' + encodeURIComponent(pair[1]));
            }
            urlEncodedData = urlEncodedDataPairs.join('&').replace(/%20/g, '+');
            req.onreadystatechange = e => {
                if (req.readyState === 4 && req.status === 200) {
                    alert("Success");
                    updateList();
                } else if (req.readyState === 4) {
                    alert("Could not perform action.\nError: " + req.status);
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