/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.barkof22.BarkoF22Projector;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.Entity.Device;
import no.ntnu.vislab.vislabcontroller.Repositories.DeviceRepository;
import no.ntnu.vislab.vislabcontroller.Repositories.DeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

/**
 *
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class ProjectorController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    private static HashMap<Integer, BarkoF22Projector> activeProjectors;

    @RequestMapping("/getProjector")
    public ResponseEntity<DummyDevice> getSingleProjector(@RequestParam(value = "id") int id) {
        DummyDevice d = new DummyBase().getSingle(id);
        System.out.println(d);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping("/getRealProjector")
    public ResponseEntity<Device> getSingleDevice(@RequestParam(value = "id") int id) {
        Device d =
    }

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        BarkoF22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.powerOn(), HttpStatus.OK);
    }

    private synchronized BarkoF22Projector getProjector(int id) throws IOException {
        if (activeProjectors == null) {
            activeProjectors = new HashMap<>();
        }
        BarkoF22Projector projector;
        if (!activeProjectors.keySet().contains(id)) {
            DummyDevice device = new DummyBase().getSingle(id);
            projector = new BarkoF22Projector(InetAddress.getByName(device.getIp()), device.getPort());
            activeProjectors.put(device.getId(), projector);
        } else {
            projector = activeProjectors.get(id);
        }
        return projector;
    }

}
