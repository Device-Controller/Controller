package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import no.ntnu.vislab.barkof22.BarkoF22Projector;
import no.ntnu.vislab.vislabcontroller.DummyBase.Device;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;

@Controller
@RequestMapping("/BarkoF22")
public class BarkoF22Controller {
    private static HashMap<Integer, BarkoF22Projector> activeProjectors;

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unMuteImage(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.unMute(), HttpStatus.OK);
    }

    @RequestMapping("/powerState")
    public ResponseEntity<String> getPowerStatus(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getPowerState() + "", HttpStatus.OK);
    }

    @RequestMapping("/lampStatus")
    public ResponseEntity<String> getLampStatus(@RequestParam(value = "id") int id, @RequestParam(value = "lampNum") int lampNum) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getLampStatus(lampNum) + "", HttpStatus.OK);
    }
    @RequestMapping("/lampRuntime")
    public ResponseEntity<String> getLampRuntime(@RequestParam(value = "id") int id, @RequestParam(value = "lampNum") int lampNum) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getLampRuntime(lampNum) + "", HttpStatus.OK);
    }
    
    @RequestMapping("/getContrast")
    public ResponseEntity<Integer> getContrast(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getContrast(), HttpStatus.OK);
    }
    @RequestMapping("/getBrightness")
    public ResponseEntity<Integer> getBrightness(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getBrightness(), HttpStatus.OK);
    }
    @RequestMapping("/getThermal")
    public ResponseEntity<Integer> getThermal(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getTemperature(), HttpStatus.OK);
    }
    @RequestMapping("/getProjectorRuntime")
    public ResponseEntity<Integer> getProjectorRuntime(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getTotalRuntime(), HttpStatus.OK);
    }

    /**
     * Returns an active projector. If the projector is not active it starts it and adds it to a list.
     *
     * @param id the id of the projector
     * @return the active projector, given by the id
     * @throws IOException if we dont have access to the port.
     */
    private synchronized BarkoF22Projector getProjector(int id) throws IOException {
        if (activeProjectors == null) {
            activeProjectors = new HashMap<>();
        }
        BarkoF22Projector projector;
        if (!activeProjectors.keySet().contains(id)) {
            Device device = new DummyBase().getSingle(id);
            projector = new BarkoF22Projector(InetAddress.getByName(device.getIp()), device.getPort());
            activeProjectors.put(device.getId(), projector);
        } else {
            projector = activeProjectors.get(id);
        }
        return projector;
    }
}
