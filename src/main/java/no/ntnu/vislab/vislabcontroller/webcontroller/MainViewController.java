package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainViewController {

    @RequestMapping("/")
    public String index() {
        return "forward:/Main/main.html";
    }

    @RequestMapping("/user")
    public String user() {
        return "forward:/User/useradmin.html";
    }

    @RequestMapping("/projector")
    public String projector() {
        return "forward:/Projector/projector.html";
    }
}