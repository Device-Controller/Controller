package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.vislabcontroller.entity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.entity.Device;
import no.ntnu.vislab.vislabcontroller.entity.DeviceGroup;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceGroupRepository;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceRepository;

@Controller
@RequestMapping("/devicegroup")
public class DeviceGroupController {
    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @RequestMapping("/groups")
    public ResponseEntity<List<DeviceGroup>> getAll() {
        return new ResponseEntity<>(deviceGroupRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> addNew(@RequestParam("groupname") String groupName
            , @RequestParam("theatre") Theatre theatre
            , @RequestBody Device[] deviceArray) {
        List<Device> devices = new ArrayList<>(Arrays.asList(deviceArray));
        DeviceGroup d = new DeviceGroup(groupName, theatre);
        devices.forEach(de->{
            d.addDevice(deviceRepository.findById(de.getId()).get());
            d.setGroupName(d.getGroupName());
        });
        return new ResponseEntity<>(deviceGroupRepository.save(d),HttpStatus.OK);
    }
}
