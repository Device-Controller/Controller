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

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;

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
    @ResponseBody
    public ResponseEntity<DeviceGroup> addOne(@RequestParam("groupname") String groupName
            , @RequestParam("theatrename") String theatreName
            , @RequestBody Device[] devices) {
        Theatre thea = theatreRepository.findByTheatreName(theatreName);
        DeviceGroup newGroup = new DeviceGroup(groupName, thea);
        for (int i = 0; i < devices.length; i++) {
            newGroup.addDevice(deviceRepository.findById(devices[i].getId()).get());
        }
        return new ResponseEntity<>(deviceGroupRepository.save(newGroup)
                , HttpStatus.OK);
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<DeviceGroup> addOne(ServletRequest request) {
        DeviceGroup dg = parseServletRequest(request);
        if(dg != null){
            return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DeviceGroup> updateName(ServletRequest request){
        DeviceGroup dg = parseServletRequest(request);
        if(dg != null){
        return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestParam ("id") int id) {
        if(deviceGroupRepository.findById(id).isPresent()){
            DeviceGroup dg = deviceGroupRepository.findById(id).get();
            deviceGroupRepository.delete(dg);
            return new ResponseEntity<>("Removed device group", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private DeviceGroup parseServletRequest(ServletRequest request) {
        String id_string = request.getParameter("id");
        String name = request.getParameter("groupName");
        String[] devices = request.getParameterValues("device-id");
        String tName = request.getParameter("theatreName");
        Theatre theatre = theatreRepository.findByTheatreName(tName);
        DeviceGroup dg = null;
        try {
            if (id_string == null || id_string.isEmpty()) {
                dg = new DeviceGroup(name, theatre);
            } else {
                int id = Integer.parseInt(id_string);
                if (deviceGroupRepository.findById(id).isPresent()) {
                    dg = deviceGroupRepository.findById(id).get();
                }
                dg.setGroupName(name);
                dg.setTheatre(theatre);
            }
            dg.getDevices().clear();
            if(devices != null) {
                for (int i = 0; i < devices.length; i++) {
                    int deviceId = Integer.parseInt(devices[i]);
                    if (deviceRepository.findById(deviceId).isPresent()) {
                        Device device = deviceRepository.findById(deviceId).get();
                        dg.addDevice(device);
                    }
                }
                return dg;
            }
            return dg;
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
