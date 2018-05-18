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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/devicegroup")
public class DeviceGroupController {
    @Autowired
    DeviceGroupRepository deviceGroupRepository;
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<DeviceGroup>> getAll() {
        return new ResponseEntity<>(deviceGroupRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public ResponseEntity<DeviceGroup> add(@RequestParam ("groupname") String groupName
            , @RequestParam("theatrename") String theatreName
            , @RequestBody Device[] devices) {
        Theatre thea = theatreRepository.findByTheatreName(theatreName);
        DeviceGroup newGroup = new DeviceGroup(groupName, thea);
        for(int i = 0; i< devices.length; i++){
            newGroup.addDevice(deviceRepository.findById(devices[i].getId()).get());
        }
        return new ResponseEntity<>(deviceGroupRepository.save(newGroup)
                , HttpStatus.OK);
    }

    @RequestMapping(value = "/setifdefault"
            , method = RequestMethod.GET
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceGroup>> setIfDefault(@RequestParam("ids") Integer[] ids
            , @RequestParam("default") boolean isDefaultDGroup) {
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

    @RequestMapping(value = "/remove"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestBody DeviceGroup[] deviceGroupArray) {
        List<DeviceGroup> deviceGroups = new ArrayList<>(Arrays.asList(deviceGroupArray));
        deviceGroupRepository.deleteAll(deviceGroups);
        return new ResponseEntity<>("Removed device groups", HttpStatus.OK);
    }
}
