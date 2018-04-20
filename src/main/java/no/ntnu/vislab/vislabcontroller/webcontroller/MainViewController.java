package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import no.ntnu.vislab.vislabcontroller.repositories.DeviceRepository;

@Controller
public class MainViewController extends DeviceController {
    @Autowired
    DeviceRepository deviceRepository;
    @RequestMapping("/")
    public String index() {
        return "forward:/Main/main.html";
    }

    @RequestMapping("/user")
    public String user() {
        return "forward:/User/user.html";
    }

    @RequestMapping("/admin")
    public String admin(){return "forward:/Admin/admin.html";}
}
