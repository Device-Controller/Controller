/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.vislabcontroller.DummyBase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.DummyBase.DummyBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Erik
 */
@Controller
@RequestMapping("/MainController")
public class ProjectorController {
    
    
    @RequestMapping("/getProjector")
    public ResponseEntity<DummyDevice> getSingleProjector(@RequestParam (value = "id") int id){
        DummyDevice d = new DummyBase().getSingle(id);
        System.out.println(d);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }
}
