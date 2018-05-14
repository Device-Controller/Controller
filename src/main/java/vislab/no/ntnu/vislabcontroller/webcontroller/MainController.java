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

import vislab.no.ntnu.providers.Device;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Projector;

/**
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class MainController extends DeviceManager {
    @Autowired
    private DeviceRepository deviceRepository;
    @RequestMapping("/getRealProjector")
    public ResponseEntity<Device> getSingleDevice(@RequestParam(value = "id") int id) {
        return null;
    }

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        return new ResponseEntity<>(device.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/powerOff")
    public ResponseEntity<Integer> powerOff(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        return new ResponseEntity<>(device.powerOff(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        if(device instanceof Projector) {
            return new ResponseEntity<>(((Projector)device).mute(), HttpStatus.OK);
        }
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unMuteImage(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        if(device instanceof Projector) {
        return new ResponseEntity<>(((Projector)device).unMute(), HttpStatus.OK);
        }
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @RequestMapping("/powerState")
    public ResponseEntity<Integer> powerState(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        if(device instanceof Projector) {
            return (device != null) ? new ResponseEntity<>(((Projector)device).getPowerState(), HttpStatus.OK) : new ResponseEntity<>(-1, HttpStatus.OK);
        }
            return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    private vislab.no.ntnu.providers.Device getOneDevice(int id) {
        vislab.no.ntnu.vislabcontroller.entity.Device entDevice = deviceRepository.findById(id).get();
        Device device = getDevice(id);
        if(device == null){
            if(entDevice.getDeviceInfo().getDeviceType().getType().equalsIgnoreCase("projector")) {
                device = createNewProjector(id, entDevice.getDeviceInfo().getManufacturer(), entDevice.getDeviceInfo().getModel());
            } else if(entDevice.getDeviceInfo().getDeviceType().getType().equalsIgnoreCase("sound system")){
                device = createNewDevice(id, entDevice.getDeviceInfo().getManufacturer(), entDevice.getDeviceInfo().getModel());
            }
            if(device != null) {
                device.setIpAddress(entDevice.getIpAddress());
                device.setPort(entDevice.getPort());
            }
        }
        return  device;
    }

}
