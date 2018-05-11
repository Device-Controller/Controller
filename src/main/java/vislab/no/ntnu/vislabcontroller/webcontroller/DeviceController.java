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
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> addOne(ServletRequest request) {
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        String ipAddress = request.getParameter("ipAddress");
        int port = Integer.parseInt(request.getParameter("port"));
        int xPos = Integer.parseInt(request.getParameter("xPos"));
        int yPos = Integer.parseInt(request.getParameter("yPos"));
        int rotation = Integer.parseInt(request.getParameter("rotation"));
        DeviceType type = deviceTypeRepository.findByType(request.getParameter("type"));
        DeviceInfo info = deviceInfoRepository.findByManufacturerAndModelAndDeviceType(manufacturer, model, type);
        Device device = new Device(info, ipAddress,port,xPos,yPos,rotation);
        return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.OK);
    }

    @RequestMapping(value = "/update"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Device> update(ServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        if(deviceRepository.findById(id).isPresent()) {
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

    @RequestMapping(value = "/remove"
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
}
