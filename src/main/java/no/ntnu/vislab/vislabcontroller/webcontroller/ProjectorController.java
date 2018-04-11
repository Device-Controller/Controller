/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;

import no.ntnu.vislab.vislabcontroller.dummybase.DummyBase;
import no.ntnu.vislab.vislabcontroller.dummybase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.entity.Device;
import no.ntnu.vislab.vislabcontroller.factories.ProjectorFactory;
import no.ntnu.vislab.vislabcontroller.providers.Projector;

/**
 *
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class ProjectorController {

    //@Autowired
    //private DeviceRepository deviceRepository;

    //@Autowired
    //private DeviceTypeRepository deviceTypeRepository;

    private static HashMap<Integer, Projector> activeProjectors;

    @RequestMapping("/getProjector")
    public ResponseEntity<DummyDevice> getSingleProjector(@RequestParam(value = "id") int id) {
        DummyDevice d = new DummyBase().getSingle(id);
        System.out.println(d);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping("/getRealProjector")
    public ResponseEntity<Device> getSingleDevice(@RequestParam(value = "id") int id) {
        return null;
    }

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }
    @RequestMapping("/powerState")
    public ResponseEntity<Integer> powerState(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getPowerState(), HttpStatus.OK);
    }

    private synchronized Projector getProjector(int id) throws IOException {
        if (activeProjectors == null) {
            activeProjectors = new HashMap<>();
        }
        Projector projector;
        if (!activeProjectors.keySet().contains(id)) {
            DummyDevice device = new DummyBase().getSingle(id);
            ProjectorFactory pf = ProjectorFactory.getInstance();
            projector = pf.getProjector(device.getMake(), device.getModel());
            if(projector != null) {
                projector.setIpAddress(device.getIp());
                projector.setPort(device.getPort());
                activeProjectors.put(device.getId(), projector);
            }
        } else {
            projector = activeProjectors.get(id);
        }
        return projector;
    }

}