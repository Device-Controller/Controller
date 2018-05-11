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
import java.util.List;
import java.util.Random;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.DeviceInfo;
import vislab.no.ntnu.vislabcontroller.entity.DeviceType;
import vislab.no.ntnu.vislabcontroller.entity.Role;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceInfoRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceRepository;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceTypeRepository;
import vislab.no.ntnu.vislabcontroller.repositories.RoleRepository;
import vislab.no.ntnu.vislabcontroller.repositories.TheatreRepository;
import vislab.no.ntnu.vislabcontroller.repositories.UserRepository;

@RequestMapping("/test")
@Controller
public class TestingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DeviceGroupRepository deviceGroupRepository;
    @Autowired
    private TheatreRepository theatreRepository;

    @RequestMapping("/initial")
    public ResponseEntity<String> initial() {
        String s = "";
        s += " " + deviceTypeRepository.save(new DeviceType("Projector")).toString();
        s += " " + deviceTypeRepository.save(new DeviceType("Sound System")).toString();
        s += " " + deviceInfoRepository.save(new DeviceInfo("Barko", "F22", deviceTypeRepository.findAll().iterator().next())).toString();
        s += " " + deviceInfoRepository.save(new DeviceInfo("Denon", "DN500-AV", deviceTypeRepository.findAll().get(1))).toString();
        s += " " + theatreRepository.save(new Theatre("Visualiseringslab")).toString();
        s += " " + theatreRepository.save(new Theatre("Testlab")).toString();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @RequestMapping("/testdevices")
    public ResponseEntity<String> testDevices() {
        String s = "";
        if (deviceTypeRepository.findAll().size() == 0) {
            initial();
        }
        ArrayList<Device> d = new ArrayList<>();
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 50, 550, 270));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 50, 420, 270));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 50, 290, 280));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 70, 190, 290));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 110, 110, 305));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 170, 50, 340));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 230, 50, 20));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 290, 110, 55));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 330, 190, 70));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 350, 290, 80));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 350, 420, 90));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.101.110", 1025, 350, 550, 90));

        Theatre t = theatreRepository.findByTheatreName("Testlab");
        for(Device dv : d) {
            s += deviceRepository.save(dv).toString();
            t.addDevice(dv);
            System.out.println(t.getDevices().size());
        }
        theatreRepository.save(t);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @RequestMapping("/actualdevice")
    public ResponseEntity<String> actual(@RequestParam("id") int id) {
        String s = "";
        if (deviceTypeRepository.findAll().size() == 0) {
            initial();
        }
        s += deviceRepository.save(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65." + id, 1025, 50, 550, 270)).toString();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> remove() {
        deviceRepository.deleteAll();
        return new ResponseEntity<>("Cleared all devices", HttpStatus.OK);
    }

    @RequestMapping(value = "/addGroup"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> addDeviceGroup(@RequestBody Theatre theatre){
        DeviceGroup d = new DeviceGroup("All", theatre);
        deviceRepository.findAll().forEach(d::addDevice);
        return new ResponseEntity<>(deviceGroupRepository.save(d),HttpStatus.OK);
    }
    @RequestMapping(value = "randomgroup"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceGroup> randomGroup(@RequestBody Theatre theatre){
        DeviceGroup d = new DeviceGroup("Random", theatre);
        deviceRepository.findAll().forEach(e->{
            if(new Random().nextInt(100)<50){
                d.addDevice(e);
                d.setGroupName(d.getGroupName() + " " + e.getId());
            }
        });
        return new ResponseEntity<>(deviceGroupRepository.save(d),HttpStatus.OK);
    }
    @RequestMapping("/actualdevices")
    public ResponseEntity<String> actualDevices() {
        String s = "";
        if (deviceTypeRepository.findAll().size() == 0) {
            initial();
        }
        ArrayList<Device> d = new ArrayList<>();
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.40", 1025, 50, 550, 270));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.41", 1025, 50, 420, 270));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.42", 1025, 50, 290, 280));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.43", 1025, 70, 190, 290));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.44", 1025, 110, 110, 305));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.45", 1025, 170, 50, 340));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.46", 1025, 230, 50, 20));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.47", 1025, 290, 110, 55));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.48", 1025, 330, 190, 70));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.49", 1025, 350, 290, 80));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.50", 1025, 350, 420, 90));
        d.add(new Device(deviceInfoRepository.findAll().iterator().next(), "158.38.65.51", 1025, 350, 550, 90));
        d.add(new Device(deviceInfoRepository.findAll().get(1), "158.38.65.60", 23, 150, 350, 0));

        for(Device dv : d) {
            s += deviceRepository.save(dv).toString();
            theatreRepository.findByTheatreName("Visualiseringslab").addDevice(dv);
        }
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @RequestMapping("/db")
    public ResponseEntity<List<Device>> getDevices() {
        List<Device> list = deviceRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/role"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Role> addRole(@RequestBody Role role){
        roleRepository.save(role);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @RequestMapping(value ="/role")
    public ResponseEntity<Role> getRole(){
        return new ResponseEntity<>(new Role("User"), HttpStatus.OK);
    }
}
