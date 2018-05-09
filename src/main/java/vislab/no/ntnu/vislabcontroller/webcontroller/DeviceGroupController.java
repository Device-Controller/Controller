package vislab.no.ntnu.vislabcontroller.webcontroller;

import vislab.no.ntnu.vislabcontroller.entity.Theatre;
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

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/devicegroup")
public class DeviceGroupController {
    @Autowired
    DeviceGroupRepository deviceGroupRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<DeviceGroup>> getAll() {
        return new ResponseEntity<>(deviceGroupRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/addone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> addOne(@RequestBody DeviceGroup deviceGroup) {
        return new ResponseEntity<>(deviceGroupRepository.save(deviceGroup), HttpStatus.OK);
    }

    @RequestMapping(value = "/addlist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceGroup>> addList(@RequestBody DeviceGroup[] deviceGroupArray) {
        return new ResponseEntity<>(deviceGroupRepository.saveAll(Arrays.asList(deviceGroupArray))
                , HttpStatus.OK);
    }

    @RequestMapping(value = "/makeone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> makeOne(@RequestParam("groupname") String groupName
            , @RequestBody Theatre theatre
            , @RequestBody Device[] deviceArray) {
        DeviceGroup d = new DeviceGroup(groupName, theatre, Arrays.asList(deviceArray));
        return new ResponseEntity<>(deviceGroupRepository.save(d), HttpStatus.OK);
    }

    @RequestMapping(value = "/setifdefault"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceGroup>> setIfDefault(@RequestParam("ids") Integer[] ids
            , @RequestParam("isdefault") boolean isDefaultDGroup) {
        List<DeviceGroup> dgs = deviceGroupRepository.findAllById(Arrays.asList(ids));
        dgs.forEach(d -> d.setDefaultDGroup(isDefaultDGroup));
        return new ResponseEntity<>(deviceGroupRepository.saveAll(dgs), HttpStatus.OK);
    }

    @RequestMapping(value = "/adddevices"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> addDevices(@RequestParam("id") Integer id
            , @RequestBody Device[] deviceArray) {
        DeviceGroup dg = deviceGroupRepository.findById(id).get();
        Arrays.asList(deviceArray).forEach(d -> dg.addDevice(d));
        return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
    }

    @RequestMapping(value = "/updatename"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> updateName(@RequestParam("id") Integer id
            , @RequestParam("groupname") String groupName) {
        DeviceGroup dg = deviceGroupRepository.findById(id).get();
        dg.setGroupName(groupName);
        return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
    }

    @RequestMapping(value = "/removeone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOne(@RequestBody DeviceGroup deviceGroup) {
        deviceGroupRepository.delete(deviceGroup);
        return new ResponseEntity<>("Removed device group: "
                + deviceGroup.getGroupName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/removelist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestBody DeviceGroup[] deviceGroupArray) {
        List<DeviceGroup> deviceGroups = new ArrayList<>(Arrays.asList(deviceGroupArray));
        deviceGroupRepository.deleteAll(deviceGroups);
        return new ResponseEntity<>("Removed device groups", HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> removeall() {
        deviceGroupRepository.deleteAll();
        return new ResponseEntity<>("Removed all device groups", HttpStatus.OK);
    }
}
