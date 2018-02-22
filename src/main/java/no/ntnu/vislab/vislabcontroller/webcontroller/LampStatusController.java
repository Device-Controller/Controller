package no.ntnu.vislab.vislabcontroller.webcontroller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import no.ntnu.vislab.barkof22.BarkoF22Projector;
import no.ntnu.vislab.vislabcontroller.Projector;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/controller")
public class LampStatusController {

    @RequestMapping("/lampStatus")
    public ResponseEntity<String> status(@RequestParam(value = "lampNumber", required = false, defaultValue = "World") int lampNumber, Model model) throws UnknownHostException {
        model.addAttribute("lampNumber", lampNumber);
        Projector f22 = new BarkoF22Projector(InetAddress.getByName("158.38.65.45"), 1025);
        String response = f22.getLampStatus(lampNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping("/lampRuntime")
    public String runtime(@RequestParam(value = "lampNumber", required = false, defaultValue = "World") int lampNumber, Model model) throws UnknownHostException {
        model.addAttribute("lampNumber", lampNumber);
        Projector f22 = new BarkoF22Projector(InetAddress.getByName("158.38.65.45"), 1025);
        f22.getLampRuntime(lampNumber);
        return "power";
    }

}
