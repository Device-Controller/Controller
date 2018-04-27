/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Projector;

/**
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class ProjectorController extends DeviceManager {
    @Autowired
    private DeviceRepository deviceRepository;
    @RequestMapping("/getRealProjector")
    public ResponseEntity<Device> getSingleDevice(@RequestParam(value = "id") int id) {
        return null;
    }

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/powerOff")
    public ResponseEntity<Integer> powerOff(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.powerOff(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unMuteImage(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.unMute(), HttpStatus.OK);
    }

    @RequestMapping("/powerState")
    public ResponseEntity<Integer> powerState(@RequestParam(value = "id") int id) throws IOException {
        Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getPowerState(), HttpStatus.OK);
    }

    private Projector getProjector(int id) {
        Device entDevice = deviceRepository.findById(id).get();
        vislab.no.ntnu.providers.Device device = getDevice(id);
        if(device == null){
            device = createNewProjector(id, entDevice.getDeviceInfo().getManufacturer(), entDevice.getDeviceInfo().getModel());
            device.setIpAddress(entDevice.getIpAddress());
            device.setPort(entDevice.getPort());

        }
        return (Projector) device;
    }

}
