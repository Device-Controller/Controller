package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getone"
            , method = RequestMethod.POST)
    public ResponseEntity<Device> getOne(@RequestParam("id") Optional<Integer> id
            , @RequestParam("ipAdress") Optional<String> ipAdress) {
        Device d = null;
        if (id.isPresent()) {
            if (deviceRepository.findById(id.get()).isPresent()) {
                d = deviceRepository.findById(id.get()).get();
            }
        } else if (ipAdress.isPresent()) {
            d = deviceRepository.findByIpAddress(ipAdress.get());
        } //TODO throw exception here?
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping(value = "/addone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> addOne(@RequestBody Device device) {
        return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
    }

    @RequestMapping(value = "/removeone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOne(@RequestBody Device device) {
        deviceRepository.delete(device);
        return new ResponseEntity<>("Removed device: "
                + device.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/removelist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestBody Device[] deviceArray) {
        List<Device> devices = new ArrayList<>(Arrays.asList(deviceArray));
        deviceRepository.deleteAll(devices);
        return new ResponseEntity<>("Removed devices", HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> removeAll() {
        deviceRepository.deleteAll();
        return new ResponseEntity<>("Removed all devices", HttpStatus.OK);
    }
}
