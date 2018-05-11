getDevices().then(r => buildTables(r));

function buildTables(deviceList) {
    for (let i = 0; i < deviceList.length; i++) {
        let f = deviceList[i];
        let d = new Device(f);
        buildTable(d);
    }
}

function buildTable(device) {
    let counter = device.id;
    let imageCard;
    switch (device.type) {
        case "Projector":
            imageCard = "<image src='Images/projector_icon_simple.png' alt='projector-image' class='list-icon'></image>";
            break;
        case "Sound System":
            imageCard = "<image src='Images/soundsystem_icon_simple.png' alt='soundsystem-image' class='list-icon'></image>";
            break;
    }
    let div = document.createElement('div');
    div.classList.add('table-container');
    div.innerHTML =
          "<table class='display-table' id='"+counter+"'>"
        + "<a href='device?id=" + counter + "'>"+ imageCard + "</a>"
        + "<tr class='header-row'>"
        + "<td>Attribute</td>"
        + "<td>Value</td>"
        + "</tr>"
        + `
          <tr>
          <td>Type</td>
          <td>` + device.type +`</td>
          </tr>
          <tr>
          <td>Manufacturer</td>
          <td>` + device.manufacturer +`</td>
          </tr>
          <tr>
          <td>Model</td>
          <td>` + device.model +`</td>
          </tr>
          <tr>
          <td>ID</td>
          <td>` + counter + `</td>
          </tr>
          <tr>
          <td>IP</td>
          <td>` + device.ipAddress + `</td>
          </tr>
          <tr>
          <td>Port</td>
          <td>` + device.port + `</td>
          </tr>
          <tr>
          <td>xPos</td>
          <td>` + device.xPos + `</td>
          </tr>
          <tr>
          <td>yPos</td>
          <td>` + device.yPos + `</td>
          </tr>
          <tr>
          <td>Rotation</td>
          <td>` + device.rotation + `</td>
          </tr>`
    ;

    document.body.appendChild(div);
}








