package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletRequest;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceInfo;
import vislab.no.ntnu.vislabcontroller.entity.DeviceType;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceInfoRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceTypeRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;

/**
 * @author ThomasSTodal
 *
 * This is a Spring Controller, it is responsible for fetching, adding, updating and removing Devices.
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

    /**
     *
     * @return List containing all Devices found in the database.
     */
    @RequestMapping("/getall")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceRepository.findAll(), HttpStatus.OK);
    }

    /**
     *
     * @return List containing all DeviceTypes found in the database.
     */
    @RequestMapping("/types")
    public ResponseEntity<List<DeviceType>> getTypes() {
        return new ResponseEntity<>(deviceTypeRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Looks up a single device based on the provided parameter, always searches by id if it is present
     * only looks of ipAddress if no id is provided.
     * @param id unique id for one device.
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
     * @param request Form data
     * @return ResponseEntity with the stored object, or BAD_REQUEST if invalid form data.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> add(ServletRequest request) {
        Device device = parseRequest(request);
        if (device != null) {
            return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates a Device object with the given form data
     * @param request Form data
     * @return ResponseEntity with the updated object, or BAD_REQUEST if invalid form data.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> update(ServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (deviceRepository.findById(id).isPresent()) {
            Device device = deviceRepository.findById(id).get();
            Device newDevice = parseRequest(request);
            if (newDevice != null) {
                device.setDefaultName(newDevice.getDefaultName());
                device.setIpAddress(newDevice.getIpAddress());
                device.getDeviceInfo().setManufacturer(newDevice.getDeviceInfo().getManufacturer());
                device.getDeviceInfo().setModel(newDevice.getDeviceInfo().getModel());
                device.getDeviceInfo().getDeviceType().setType(newDevice.getDeviceInfo().getDeviceType().getType());
                device.setPort(newDevice.getPort());
                device.setxPos(newDevice.getxPos());
                device.setyPos(newDevice.getyPos());
                device.setRotation(newDevice.getRotation());
                return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Removes the given devices from the deviceRepository
     * @param deviceArray An array of devices
     * @return ResponseEntity confirming that the devices have been removed.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestBody Device[] deviceArray) {
        List<Device> devices = new ArrayList<>(Arrays.asList(deviceArray));
        deviceRepository.deleteAll(devices);
        return new ResponseEntity<>("Removed devices", HttpStatus.OK);
    }

    /**
     * Attempts to parse the given form data into a Device object
     * @param request Form data
     * @return the new Device, null if invalid form data.
     */
    private Device parseRequest(ServletRequest request) {
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        DeviceType type = deviceTypeRepository.findByType(request.getParameter("type"));
        if (type == null) {
            return null;
        }
        DeviceInfo info = deviceInfoRepository.findByManufacturerAndModelAndDeviceType(manufacturer, model, type);
        if (info == null) {
            return null;
        }
        String ipAddress = request.getParameter("ipAddress");
        int port;
        try {
            port = Integer.parseInt(request.getParameter("port"));
            InetAddress.getByName(ipAddress);
        } catch (UnknownHostException | NumberFormatException ex) {
            return null;
        }
        int xPos;
        try {
            xPos = Integer.parseInt(request.getParameter("xPos"));
        } catch (NumberFormatException ex) {
            xPos = -1;
        }
        int yPos;
        try {
            yPos = Integer.parseInt(request.getParameter("yPos"));
        } catch (NumberFormatException ex) {
            yPos = -1;
        }
        int rotation;
        try {
            rotation = Integer.parseInt(request.getParameter("rotation"));
        } catch (NumberFormatException ex) {
            rotation = -1;
        }
        return new Device(name, info, ipAddress, port, xPos, yPos, rotation);
    }
}
