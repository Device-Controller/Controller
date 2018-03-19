parseURLId(location.href);
let id;

function parseURLId(url) {
    let startIndex = url.indexOf("?") + 1;
    let endIndex = url.length + 1;
    parseId(url.slice(startIndex, endIndex - 1));

}

function parseId(id) {
    let startIndex = id.indexOf("=") + 1;
    let endIndex = id.length + 1;
    fetchProjector(id.slice(startIndex, endIndex - 1));
}

function fetchProjector(id) {
    fetch('controller/getProjector?id=' + id).then(response => {
        if (response.ok) {
            this.id = id;
            response.json().then(p => console.log(p));
        }
    });
}