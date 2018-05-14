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

    @RequestMapping("/getall")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/types")
    public ResponseEntity<List<DeviceType>> getTypes(){
        return new ResponseEntity<>(deviceTypeRepository.findAll(), HttpStatus.OK);
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> addOne(ServletRequest request) {
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        String ipAddress = request.getParameter("ipAddress");
        int port;
        try {
            port = Integer.parseInt(request.getParameter("port"));
            InetAddress.getByName(ipAddress);
        } catch (UnknownHostException | NumberFormatException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        DeviceType type = deviceTypeRepository.findByType(request.getParameter("type"));
        DeviceInfo info = deviceInfoRepository.findByManufacturerAndModelAndDeviceType(manufacturer, model, type);
        if(type != null && info != null) {
            Device device = new Device(info, ipAddress, port, xPos, yPos, rotation);
            return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> update(ServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (deviceRepository.findById(id).isPresent()) {
            Device device = deviceRepository.findById(id).get();
            String manufacturer = request.getParameter("manufacturer");
            String model = request.getParameter("model");
            String ipAddress = request.getParameter("ipAddress");
            String type = request.getParameter("type");
            int port = Integer.parseInt(request.getParameter("port"));
            int xPos = Integer.parseInt(request.getParameter("xPos"));
            int yPos = Integer.parseInt(request.getParameter("yPos"));
            int rotation = Integer.parseInt(request.getParameter("rotation"));
            device.setIpAddress(ipAddress);
            device.getDeviceInfo().setManufacturer(manufacturer);
            device.getDeviceInfo().setModel(model);
            device.getDeviceInfo().getDeviceType().setType(type);
            device.setPort(port);
            device.setxPos(xPos);
            device.setyPos(yPos);
            device.setRotation(rotation);
            return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOne(@RequestParam ("id") int id) {
        try {
            if (deviceRepository.findById(id).isPresent()) {
                Device device = deviceRepository.findById(id).get();

                deviceRepository.delete(device);
                return new ResponseEntity<>("Removed device: "
                        + device.getId(), HttpStatus.OK);
            }
        } catch (NumberFormatException ex) {
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/removelist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestBody Device[] deviceArray) {
        List<Device> devices = new ArrayList<>(Arrays.asList(deviceArray));
        deviceRepository.deleteAll(devices);
        return new ResponseEntity<>("Removed devices", HttpStatus.OK);
    }
}
