let devices = [];

fetch('../test/db')
    .then(response => {
        if (response.ok) {
            response.json()
                .then(e => {
                    devices.push.apply(devices, e);
                })
        }
    })
    .then(loadDevices)
    .then(function (msg) {
        return new Promise(function (resolve, reject) {
            setTimeout(function () {
                resolve();
            }, 4000)
        })
    })
    .then(function () {
    })
    .catch(err => console.log(error));

function loadDevices() {
    for (let numberOfDevices = 1; numberOfDevices < devices.length + 1; numberOfDevices++) {
        let table = document.createElement('table');

        table.innerHTML =
            "<tr class='header-row'>"
            + "<td>Setting</td>"
            + "<td>Current Value</td>"
            + "<td>New Value</td>"
            + "</tr>"
            + `<tr>
            <td>Power</td>
            <td id="power-setting">Loading...</td>
        <td>
        <select>
        <option>On</option>
        <option>Off</option>
        </select>
        </td>
        </tr>
        <tr>
        <td>PowerState</td>
        <td id="get-powerstate">Loading...</td>
        </tr>
        <tr>
        <td>Mute</td>
        <td id="mute-state">Loading...</td>
        <td>
        <select>
        <option>Mute</option>
        <option>Unmute</option>
        </select>
        </td>
        </tr>
        <tr>
        <td>Test Image</td>
        <td id="test-image">Loading...</td>
        <td>
        <select>
        <option>Off</option>
        <option>Image 1</option>
        <option>Image 2</option>
        <option>Image 3</option>
        <option>Image 4</option>
        <option>Image 5</option>
        <option>Image 6</option>
        <option>Image 7</option>
        </select>
        </td>
        </tr>
        <tr>
        <td>Brightness</td>
        <td id="brightness">Loading...</td>
        <td>
        <div class="slider-container">
            <input type="range" min="-100" max="100" value="0" class="slider" id="brightness" oninput="brightnessFinder()">
            <span id="brightness-value">0</span>
            </div>
            <script>
            function brightnessFinder() {
                let slider = document.getElementById('brightness').value;
                document.getElementById('brightness-value').innerHTML = slider;
            }
            </script>
            </td>
            </tr>
            <tr>
            <td>Contrast</td>
            <td id="get-contrast">Loading...</td>
        <td>
        <div class="slider-container">
            <input type="range" min="-100" max="100" value="0" class="slider" id="contrast" oninput="contrastFinder()">
            <span id="contrast-value">0</span>
            </div>
            <script>
            function contrastFinder() {
                let slider = document.getElementById('contrast').value;
                document.getElementById('contrast-value').innerHTML = slider;
            }
            </script>
            </td>
            </tr>
            <tr>
            <td>Thermal</td>
            <td id="temperature">Loading...</td>
        </tr>
        <tr>
        <td>Projector Runtime</td>
        <td id="projector-runtime">Loading...</td>
        </tr>
        <tr>
        <td>Lamp 1 Runtime</td>
        <td id="lamp1-runtime">Loading...</td>
        </tr>
        <tr class="bottom-row">
        <td>Lamp Status</td>
        <td id="lamp1-status">Loading...</td>
        </tr>`
        ;

        console.log(table);
        console.log(document.body);
        document.body.appendChild(table);
        updateData(numberOfDevices);

    }
}

function updateData(id) {
    //GET CONTRAST
    fetch('../BarkoF22/getContrast?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("get-contrast").innerHTML = e);
        }
    });

//GET POWERSTATE
    fetch('../BarkoF22/powerState?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("get-powerstate").innerHTML = e);
        }
    });

//GET BRIGHTNESS
    fetch('../BarkoF22/getBrightness?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("brightness").innerHTML = e);
        }
    });

//GET TEMPERATURE
    fetch('../BarkoF22/getThermal?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("temperature").innerHTML = e);
        }
    });

//GET PROJECTOR RUNTIME
    fetch('../BarkoF22/getProjectorRuntime?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("projector-runtime").innerHTML = e);
        }
    });

//GET LAMPRUNTIME 1
    fetch('../BarkoF22/lampRuntime?id=' + id + '&lampNum=1').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp1-runtime").innerHTML = e);
        }
    });

//GET LAMPSTATUS 1
    fetch('../BarkoF22/lampStatus?id=' + id + '&lampNum=1').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp1-status").innerHTML = e);
        }
    });

//GET LAMPRUNTIME 2
    fetch('../BarkoF22/lampRuntime?id=' + id + '&lampNum=2').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp2-runtime").innerHTML = e);
        }
    });

//GET LAMPSTATUS 2
    fetch('../BarkoF22/lampStatus?id=' + id + '&lampNum=2').then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("lamp2-status").innerHTML = e);
        }
    });
}




