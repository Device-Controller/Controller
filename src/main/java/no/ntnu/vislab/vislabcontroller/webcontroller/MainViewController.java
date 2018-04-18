package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import no.ntnu.vislab.vislabcontroller.repositories.DeviceRepository;

@Controller
public class MainViewController extends MainController{
    @Autowired
    DeviceRepository deviceRepository;
    @RequestMapping("/")
    public String index() {
        return "forward:/Main/main.html";
    }

    @RequestMapping("/user")
    public String user() {
        return "forward:/User/useradmin.html";
    }
//
//    @RequestMapping("/device")
//    public String projector(@RequestParam("id") int id){
//        if(deviceRepository.findById(id).isPresent()){
//            Device d = getProjector(id);
//            if(d != null) {
//                return d.getDeviceSpecificPage();
//            }
//            no.ntnu.vislab.vislabcontroller.entity.Device device = deviceRepository.findById(id).get();
//            d = DeviceFactory.getInstance().getDevice(device.getDeviceInfo().getManufacturer(),device.getDeviceInfo().getModel());
//            if(d != null){
//                return d.getDeviceSpecificPage();
//            }
//            d = DeviceFactory.getInstance().getDevice(device.getDeviceInfo().getManufacturer(),device.getDeviceInfo().getModel());
//            if (d != null){
//                return d.getDeviceSpecificPage();
//            }
//        }
//        return "";
//    }
}
