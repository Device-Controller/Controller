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
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

import java.util.*;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    TheatreRepository theatreRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getone"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> getOne(@RequestParam("id") Optional<Integer> id
            , @RequestParam("ipAddress") Optional<String> ipAddress) {
        Device d = null;
        if (id.isPresent()) {
            if (deviceRepository.findById(id.get()).isPresent()) {
                d = deviceRepository.findById(id.get()).get();
            }
        } else if (ipAddress.isPresent()) {
            d = deviceRepository.findByIpAddress(ipAddress.get());
        } //TODO throw exception here?
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Device>> addOne(@RequestBody Device[] deviceArray) {
        return new ResponseEntity<>(deviceRepository.saveAll(Arrays.asList(deviceArray)), HttpStatus.OK);
    }

    @RequestMapping(value = "/updatename"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> updateName(@RequestParam("id") Integer id
            , @RequestParam("name") String name) {
        Device d = deviceRepository.findById(id).get();
        d.setDefaultName(name);
        return new ResponseEntity<>(deviceRepository.save(d), HttpStatus.OK);
    }

    @RequestMapping(value = "/updatexposition"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> updateXPos(@RequestParam("id") Integer id
            , @RequestParam("xposition") int xPosition) {
        Device d = deviceRepository.findById(id).get();
        d.setxPos(xPosition);
        return new ResponseEntity<>(deviceRepository.save(d), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateypos"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> updateYPos(@RequestParam("id")  Integer id
            , @RequestParam("yposition") int yPosition) {
        Device d = deviceRepository.findById(id).get();
        d.setyPos(yPosition);
        return new ResponseEntity<>(deviceRepository.save(d), HttpStatus.OK);
    }

    @RequestMapping(value = "/updaterotation"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> updateRotation(@RequestParam("id")  Integer id
            , @RequestParam("rotation") int rotation) {
        Device d = deviceRepository.findById(id).get();
        d.setRotation(rotation);
        return new ResponseEntity<>(deviceRepository.save(d), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateaddress"
            , method = RequestMethod.GET)
    public ResponseEntity<Device> updateAddress(@RequestParam("id") Integer id
            , @RequestParam("ipAddress") Optional<String> ipAddress
            , @RequestParam("port") Optional<Integer> port) {
        Device d = deviceRepository.findById(id).get();
        if(ipAddress.isPresent())
            d.setIpAddress(ipAddress.get());
        if(port.isPresent())
            d.setPort(port.get());
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestBody Device[] deviceArray) {
        List<Device> devices = new ArrayList<>(Arrays.asList(deviceArray));
        deviceRepository.deleteAll(devices);
        return new ResponseEntity<>("Removed devices", HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> removeAll() {
        List<Theatre> th = theatreRepository.findAll();
        for(Theatre t : th) {
            t.removeAllDevices();
        }
        deviceRepository.deleteAll();
        return new ResponseEntity<>("Removed all devices", HttpStatus.OK);
    }
}
