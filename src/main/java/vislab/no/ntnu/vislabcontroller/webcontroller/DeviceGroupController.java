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
 *
 * This is a Spring Controller, it is responsible for fetching, adding, updating and removing DeviceGroups.
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

    /**
     *
     * @return List containing all DeviceGroups in the database
     */
    @RequestMapping("/getall")
    public ResponseEntity<List<DeviceGroup>> getAll() {
        return new ResponseEntity<>(deviceGroupRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Creates and adds a DeviceGroup with the given parameters to the database
     * @param groupName Name of the group
     * @param theatreName Name of the Theatre the group belongs to
     * @param devices Array of devices
     * @return ResponseEntity with the created group.
     */
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DeviceGroup> add(@RequestParam("groupname") String groupName
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

    /**
     * Converts form data to a DeviceGroup object and stores it in a deviceGroupRepository
     * @param request Form data
     * @return The stored object, or BAD_REQUEST if invalid form data.
     */
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<DeviceGroup> add(ServletRequest request) {
        DeviceGroup dg = parseRequest(request);
        if(dg != null){
            return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Looks up the given ids and sets the found DeviceGroups as default or not default
     * @param ids Array of ids
     * @param isDefaultDGroup Whether or not the groups should be default
     * @return ResponseEntity with the updated DeviceGroups.
     */
    @RequestMapping(value = "/setifdefault"
            , method = RequestMethod.GET
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceGroup>> setIfDefault(@RequestParam("ids") Integer[] ids
            , @RequestParam("default") boolean isDefaultDGroup) {
        List<DeviceGroup> dgs = deviceGroupRepository.findAllById(Arrays.asList(ids));
        dgs.forEach(d -> d.setDefaultDGroup(isDefaultDGroup));
        return new ResponseEntity<>(deviceGroupRepository.saveAll(dgs), HttpStatus.OK);
    }

    /**
     * Adds the given Devices to the given DeviceGroup
     * @param id identifies which DeviceGroup to modify
     * @param deviceArray Devices to add
     * @return ResponseEntity with the modified DeviceGroup
     */
    @RequestMapping(value = "/adddevices"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> addDevices(@RequestParam("id") Integer id
            , @RequestBody Device[] deviceArray) {
        DeviceGroup dg = deviceGroupRepository.findById(id).get();
        Arrays.asList(deviceArray).forEach(d -> dg.addDevice(d));
        return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
    }

    /**
     * Updates a DeviceGroup with the given form data
     * @param request Form data
     * @return ResponseEntity with the updated DeviceGroup, or BAD_REQUEST if invalid form data
     */
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DeviceGroup> update(ServletRequest request){
        DeviceGroup dg = parseRequest(request);
        if(dg != null){
        return new ResponseEntity<>(deviceGroupRepository.save(dg), HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Removes the DeviceGroup matching the gven id
     * @param id DeviceGroup id
     * @return ResponseEntity confirming that the DeviceGroup has been removed, or BAD_REQUEST if invalid id.
     */
    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestParam ("id") int id) {
        if(deviceGroupRepository.findById(id).isPresent()){
            DeviceGroup dg = deviceGroupRepository.findById(id).get();
            deviceGroupRepository.delete(dg);
            return new ResponseEntity<>("Removed device group", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Attempts to parse the given form data into a DeviceGroup object
     * @param request Form data
     * @return the new DeviceGroup, null if invalid form data.
     */
    private DeviceGroup parseRequest(ServletRequest request) {
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
