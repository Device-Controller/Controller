/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller.webcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import no.ntnu.vislab.barkof22.BarkoF22Projector;
import no.ntnu.vislab.vislabcontroller.DummyBase.Device;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class ProjectorController {

    private static HashMap<Integer, BarkoF22Projector> activeProjectors;

    @RequestMapping("/getProjector")
    public ResponseEntity<Device> getSingleProjector(@RequestParam(value = "id") int id) {
        Device d = new DummyBase().getSingle(id);
        System.out.println(d);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }

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
