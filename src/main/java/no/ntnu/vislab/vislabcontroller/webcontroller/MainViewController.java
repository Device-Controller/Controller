package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import no.ntnu.vislab.vislabcontroller.entity.Device;
import no.ntnu.vislab.vislabcontroller.entity.DeviceGroup;
import no.ntnu.vislab.vislabcontroller.entity.DeviceInfo;
import no.ntnu.vislab.vislabcontroller.entity.DeviceType;
import no.ntnu.vislab.vislabcontroller.entity.Role;
import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceGroupRepository;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceInfoRepository;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceRepository;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceTypeRepository;
import no.ntnu.vislab.vislabcontroller.repositories.RoleRepository;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;

@Controller
public class MainViewController {
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

    @RequestMapping("/")
    public String index() {
        return "forward:/Main/main.html";
    }

    @RequestMapping("/user")
    public String user() {
        return "forward:/User/useradmin.html";
    }

    @RequestMapping("FUCK")
    public String testing(){
        String s = roleRepository.save(new Role("ADMIN")).toString();
        s += " " + deviceTypeRepository.save(new DeviceType("Projector")).toString();
        s += " " + deviceInfoRepository.save(new DeviceInfo("Barko", "F22", deviceTypeRepository.findAll().iterator().next())).toString();
        s += " " + deviceRepository.save(new Device("158.38.101.110",1025,50,50,90,deviceInfoRepository.findAll().iterator().next())).toString();
        s += " " + userRepository.save(new User("mongo", "dildo", "yolo@yolo.gov", roleRepository.findAll().iterator().next())).toString();
        s += " " + deviceGroupRepository.save(new DeviceGroup()).toString();
        DeviceGroup d = deviceGroupRepository.findAll().iterator().next();
        User u  = userRepository.findAll().iterator().next();
        d.addDevice(deviceRepository.findAll().get(0));
        d.addUser(u);
        deviceGroupRepository.save(d);
        User u2  = userRepository.findAll().iterator().next();
        System.out.println(u2.getDeviceGroups().size());
        return "LUL";
    }
}
