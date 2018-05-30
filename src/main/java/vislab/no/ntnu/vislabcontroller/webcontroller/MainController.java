package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Device;
import vislab.no.ntnu.providers.Projector;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;

/**
 * @author Erik
 * <p>
 * This is a Spring Controller. Handles Power on/off, mute/unmute and state of power for all Devices
 */
@Controller
@RequestMapping("/api/main")
public class MainController extends DeviceManager {
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping("/powerOn")
    public ResponseEntity<Integer> powerOn(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        return new ResponseEntity<>(device.powerOn(), HttpStatus.OK);
    }

    @RequestMapping("/powerOff")
    public ResponseEntity<Integer> powerOff(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        return new ResponseEntity<>(device.powerOff(), HttpStatus.OK);
    }

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        if (device instanceof Projector) {
            return new ResponseEntity<>(((Projector) device).mute(), HttpStatus.OK);
        }
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unMuteImage(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        if (device instanceof Projector) {
            return new ResponseEntity<>(((Projector) device).unMute(), HttpStatus.OK);
        }
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @RequestMapping("/powerState")
    public ResponseEntity<Integer> powerState(@RequestParam(value = "id") int id) throws IOException {
        Device device = getOneDevice(id);
        return (device != null) ? new ResponseEntity<>((device).getPowerState(), HttpStatus.OK) : new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @RequestMapping("/supported")
    public ResponseEntity<List<WrapperType>> getSupportedTypes() {
        List<WrapperType> types = new ArrayList<>();
        getSupportedDevices().forEach(device -> {
            if (!types.contains(new WrapperType(device.getMake()))) {
                WrapperType wt = new WrapperType(device.getMake());
                wt.getModels().add(device.getModel());
                types.add(wt);
            } else {
                types.forEach(t -> {
                    if (!t.getModels().contains(device.getModel())) {
                        t.getModels().add(device.getModel());
                    }
                });
            }
        });
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    private vislab.no.ntnu.providers.Device getOneDevice(int id) {
        if (deviceRepository.findById(id).isPresent()) {
            vislab.no.ntnu.vislabcontroller.entity.Device entDevice = deviceRepository.findById(id).get();
            Device device = getDevice(id);
            if (device == null) {
                if (entDevice.getDeviceInfo().getDeviceType().getType().equalsIgnoreCase("projector")) {
                    device = createNewProjector(id, entDevice.getDeviceInfo().getManufacturer(), entDevice.getDeviceInfo().getModel());
                } else {
                    device = createNewDevice(id, entDevice.getDeviceInfo().getManufacturer(), entDevice.getDeviceInfo().getModel());
                }
                if (device != null) {
                    device.setIpAddress(entDevice.getIpAddress());
                    device.setPort(entDevice.getPort());
                    device.initialize();
                }
            }

            return device;
        }
        return null;
    }

    private class WrapperType {
        private String manufacturer;
        private List<String> models = new ArrayList<>();

        public WrapperType(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public List<String> getModels() {
            return models;
        }

        public void setModels(List<String> models) {
            this.models = models;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WrapperType)) return false;
            WrapperType that = (WrapperType) o;
            return Objects.equals(getManufacturer(), that.getManufacturer());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getManufacturer());
        }
    }
}
