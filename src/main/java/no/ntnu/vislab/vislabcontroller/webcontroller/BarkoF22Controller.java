package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.InetAddress;

import no.ntnu.vislab.barkof22.BarkoF22Projector;
import no.ntnu.vislab.vislabcontroller.DummyBase.Device;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;

@Controller
@RequestMapping("/BarkoF22")
public class BarkoF22Controller {
    private BarkoF22Projector projector = null;
    @RequestMapping("/mute")
    public ResponseEntity<Integer>muteImage(@RequestParam (value = "id") int id) throws IOException {
        if(projector == null){
            Device device = new DummyBase().getSingle(id);
            projector = new BarkoF22Projector(InetAddress.getByName(device.getIp()), device.getPort());
        }
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }
    @RequestMapping("/unMute")
    public ResponseEntity<Integer>unMuteImage(@RequestParam (value = "id") int id) throws IOException {
        if(projector == null){
            Device device = new DummyBase().getSingle(id);
            projector = new BarkoF22Projector(InetAddress.getByName(device.getIp()), device.getPort());
        }
        return new ResponseEntity<>(projector.unMute(), HttpStatus.OK);
    }
}
