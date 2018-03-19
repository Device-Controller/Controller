package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import no.ntnu.vislab.vislabcontroller.DummyBase.Device;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;

@Controller
@RequestMapping("/controller")
public class DeviceController {
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
    @RequestMapping("/db")
    public ResponseEntity<List<Device>> devices(){
        return new ResponseEntity<>(new DummyBase().getList(), HttpStatus.OK);
    }

    @RequestMapping("/getProjector")
    public ResponseEntity<Device> getSingleProjector(@RequestParam (value = "id") int id){
        Device d = new DummyBase().getSingle(id);
        System.out.println(d);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }
}
