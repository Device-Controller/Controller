

getDevices().then(r => buildTables(r));

function buildTables(deviceList) {
    for (let i = 0; i < deviceList.length; i++) {
        let f = deviceList[i];
        let d = new Device(f);
        buildTable(d);
    }
}
function buildTable(device) {
        let div = document.createElement('div');
        div.classList.add('table-container');
        div.innerHTML =
              "<table class='display-table'>"
            + "<tr class='header-row'>"
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
        <td>
                    <select id="muteList">
                        <option value="mute">Mute</option>
                        <option value="unMute">Unmute</option>
                    </select>
                </td>
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
                <td>
                    <input type="number" id="brightness-value" value="0" min="-100" max="100"
                           onload="setValue(this, brightness)" style="width:50px;">
                </td>
                <td>
                    <div class="slider-container">
                        <input type="range" min="-100" max="100" value="0" class="slider" id="brightness"
                               oninput="brightnessFinder()" onmouseup="setBrightness(this)">
                    </div>
                    <script>
                        function brightnessFinder() {
                            let slider = document.getElementById('brightness').value;
                            document.getElementById('brightness-value').value = slider;
                        }
                    </script>
                </td>
            </tr>
            <tr>
                <td>Contrast</td>
                <td>
                    <input type="number" id="contrast-value" value="0" min="-100" max="100"
                           onload="setValue(this, contrast)" style="width:50px;">
                </td>
                <td>
                    <div class="slider-container">
                        <input type="range" min="-100" max="100" value="0" class="slider" id="contrast"
                               oninput="contrastFinder()" onmouseup="setContrast(this)">
                    </div>
                    <script>
                        function contrastFinder() {
                            let slider = document.getElementById('contrast').value;
                            document.getElementById('contrast-value').value = slider;
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
        </tr>
        </table>`
        ;

        document.body.appendChild(div);
        updateData(device.id);
}

document.getElementById("muteList").onchange = function() {
    if (this.value == 'mute') {
        mute();
    } else if (this.value == 'unMute') {
        unMute();
    }
};

function mute() {
    fetch('BarkoF22/mute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

function unMute() {
    fetch('BarkoF22/unMute?id=' + parseURLId(location.href)).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

document.getElementById("imageList").onchange = function() {
    console.log(this.value);
    testImage(this.value);
}

function testImage(image) {
    fetch('BarkoF22/testImage?id=' + parseURLId(location.href) + '&image=' + image).then(response => {
        if (response.ok) {
            response.json().then(e => console.log(e));
        }
    })
}

document.querySelector('#contrast-value').addEventListener('keypress', function (e) {
    var key = e.which || e.keyCode;
    if (key === 13) {
        setContrast(document.querySelector('#contrast-value'));
        document.querySelector('#contrast').value = document.querySelector('#contrast-value').value;
    }
});

function setContrast(number) {
    fetch('BarkoF22/setContrast?id=' + parseURLId(location.href) + '&value=' + number.value).then(response => {
        if (response.ok) {
            console.log(number.value);
            console.log(response);
            response.json().then(e => document.getElementById("contrast-value").innerHTML = e);
        }
    });
}
function setBrightness(number) {
    fetch('BarkoF22/setBrightness?id=' + parseURLId(location.href) + '&value=' + number.value).then(response => {
        if (response.ok) {
            console.log(number.value);
            console.log(response);
            response.json().then(e => document.getElementById("brightness-value").innerHTML = e);
        }
    });
}
function setValue(element1, element2) {
    element1.value = document.getElementById(element2).value;
}

function updateData(id) {
    //GET CONTRAST
    fetch('../BarkoF22/getContrast?id=' + id).then(response => {
        if (response.ok) {
            response.json().then(e => document.getElementById("contrast-value").innerHTML = e);
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
}




