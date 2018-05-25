package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletRequest;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceInfo;
import vislab.no.ntnu.vislabcontroller.entity.DeviceType;
import vislab.no.ntnu.vislabcontroller.exception.InvalidEntityConfigException;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceInfoRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceTypeRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

/**
 * Controller for managing the Device entity. Supports CRUD to a database through form submissions.
 *
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DeviceInfoRepository deviceInfoRepository;
    @Autowired
    DeviceTypeRepository deviceTypeRepository;
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    /**
     * @return List containing all Devices found in the database.
     */
    @RequestMapping("/getall")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceRepository.findAll(), HttpStatus.OK);
    }

    /**
     * @return List containing all DeviceTypes found in the database.
     */
    @RequestMapping("/types")
    public ResponseEntity<List<DeviceType>> getTypes() {
        return new ResponseEntity<>(deviceTypeRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Looks up a single device based on the provided parameter, always searches by id if it is present
     * only looks of ipAddress if no id is provided.
     *
     * @param id        unique id for one device.
     * @param ipAddress ip address, usually unique for one device.
     * @return ResponseEntity with the found device, null if none is found or both parameters are missing.
     */
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
        }
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    /**
     * Converts form data to a Device object and saves it to a deviceRepository
     *
     * @param request Form data
     * @return ResponseEntity with the stored object, or BAD_REQUEST if invalid form data.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> add(ServletRequest request) {
        return handleRequest(request);
    }

    /**
     * Updates a Device object with the given form data
     *
     * @param request Form data
     * @return ResponseEntity with the updated object, or BAD_REQUEST if invalid form data.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> update(ServletRequest request) {
        return handleRequest(request);
    }

    private ResponseEntity<String> handleRequest(ServletRequest request){
        Device device = null;
        try {
            device = parseRequest(request);
            deviceRepository.save(device);
            String returnString = generateString(device);
            return new ResponseEntity<>(returnString, HttpStatus.OK);
        } catch (InvalidEntityConfigException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private String generateString(Device device) {
        StringBuilder str = new StringBuilder();
        str.append("Device ");
        if(device.getDefaultName() != null){
            str.append(device.getDefaultName());
        } else {
            str.append(device.getDeviceInfo().getManufacturer());
            str.append(", ");
            str.append(device.getDeviceInfo().getModel());
        }
        str.append(" was added");
        return str.toString();
    }

    /**
     * Removes the given devices from the deviceRepository
     *
     * @param id Device id
     * @return ResponseEntity confirming that the devices have been removed.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestParam("id") int id) {
        Device device = null;
        if (deviceRepository.findById(id).isPresent()) {
            device = deviceRepository.findById(id).get();
            int numGroups = deviceGroupRepository.findAllByDevices(Collections.singletonList(device)).size();
            int numTheatre = theatreRepository.findAllByDevices(Collections.singletonList(device)).size();
            if(numGroups == 0 && numTheatre == 0) {
                deviceRepository.delete(deviceRepository.findById(id).get());
                return new ResponseEntity<>("Removed device with ID: " + id, HttpStatus.OK);
            }
            String group = (numGroups > 0) ? numGroups + " groups" : "";
            String theatre = (numTheatre > 0) ? numTheatre + " theatres" : "";
            String finalString = !(group.isEmpty() || theatre.isEmpty()) ? group + " and "+ theatre : group + theatre;
            return new ResponseEntity<>("Device: " + id + " belongs to " + finalString, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Could not remove device with ID: "  + id, HttpStatus.OK);
    }

    /**
     * Attempts to parse the given form data into a Device object
     *
     * @param request Form data
     * @return the new Device, null if invalid form data.
     */
    private Device parseRequest(ServletRequest request) throws InvalidEntityConfigException {
        String id_string = request.getParameter("id");
        Device device = null;
        int id;
        if (id_string != null && !id_string.isEmpty()) {
            try {
                id = Integer.parseInt(id_string);
            } catch (NumberFormatException e) {
                throw new InvalidEntityConfigException("ID was not a number");
            }
            if (deviceRepository.findById(id).isPresent()) {
                device = deviceRepository.findById(id).get();
            }
        }
        if(device == null){
            device = new Device();
        }
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        String typeName = request.getParameter("type");
        String ipAddress = request.getParameter("ipAddress");
        String port_string = request.getParameter("port");
        String x_pos = request.getParameter("xPos");
        String y_pos = request.getParameter("yPos");
        String rotation_string = request.getParameter("rotation");
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            throw new InvalidEntityConfigException("Ip-address invalid: " + ipAddress);
        }
        DeviceType type = deviceTypeRepository.findByType(typeName);
        if(type == null){
            throw new InvalidEntityConfigException("Invalid type: " + typeName);
        }
        DeviceInfo deviceInfo = deviceInfoRepository.findByManufacturerAndModelAndDeviceType(manufacturer, model, type);
        if(deviceInfo == null){
            deviceInfo = new DeviceInfo(manufacturer, model, type);
            deviceInfoRepository.save(deviceInfo);
        }
        int port;
        try{
            port = Integer.parseInt(port_string);
        } catch (NumberFormatException ex){
            throw new InvalidEntityConfigException("Port was not a number: " + port_string);
        }
        int xPos, yPos, rotation;
        try{
            xPos = Integer.parseInt(x_pos);
            yPos = Integer.parseInt(y_pos);
            rotation = Integer.parseInt(rotation_string);
        } catch (NumberFormatException e){
            xPos = -1;
            yPos = -1;
            rotation = -1;
        }
        device.setDefaultName(name);
        device.setDeviceInfo(deviceInfo);
        device.setIpAddress(ipAddress);
        device.setPort(port);
        device.setRotation(rotation);
        device.setxPos(xPos);
        device.setyPos(yPos);
        return device;
    }
}
