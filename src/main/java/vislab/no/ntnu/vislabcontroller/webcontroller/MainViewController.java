package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vislab.no.ntnu.DeviceManager;

/**
 *
 * This is a Spring controller, it is responsible for serving the corresponding webpages to each query
 */
@Controller
public class MainViewController extends DeviceManager{
    @RequestMapping("/")
    public String index() {
        return "forward:/Main/main.html";
    }

    @RequestMapping("/user")
    public String user() {
        return "forward:/User/user.html";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(){
        return "forward:/Admin/admin.html";
    }

    @RequestMapping("/login")
    public String login(){
        return "forward:/Login/login.html";
    }

    @RequestMapping("/device")
    public String devicePage(@RequestParam("id") int id){
        return super.locateDevicePage(id);
    }

    @RequestMapping("/devicesettings")
    public String devicesettings() { return "forward:/DeviceControls/devicecontrols.html"; }
}
