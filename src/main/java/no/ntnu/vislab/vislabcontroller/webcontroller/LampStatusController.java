package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;

import no.ntnu.vislab.barkof22.BarkoF22Interface;
import no.ntnu.vislab.barkof22.BarkoF22InterfaceImpl;

@Controller
@RequestMapping("/controller")
public class LampStatusController {
    private static BarkoF22Interface projector;

    @RequestMapping("/lampStatus")
    public ResponseEntity<Integer> status(@RequestParam(value = "lampNumber", required = false, defaultValue = "1") int lampNumber, Model model) throws Exception {
        model.addAttribute("lampNumber", lampNumber);
        if(projector == null){
            projector = new BarkoF22InterfaceImpl("MONG Ltd", "ERIK", InetAddress.getByName("158.38.101.110"), 1025);
        }
        Integer response = projector.getLampStatus(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping("/lampRuntime")
    public ResponseEntity<Integer> runtime(@RequestParam(value = "lampNumber", required = false, defaultValue = "World") int lampNumber, Model model) throws Exception {
        model.addAttribute("lampNumber", lampNumber);
        if(projector == null){
            projector = new BarkoF22InterfaceImpl("MONG Ltd", "ERIK", InetAddress.getByName("158.38.101.110"), 1025);
        }
        int response= projector.getLampRuntime(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/testDummy")
    public ResponseEntity<String> dummy() {
        long time = System.currentTimeMillis();
        String response = "blue";
        if (time % 2 == 0) {
            response = "red";
        } else {
            response = "blue";
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
