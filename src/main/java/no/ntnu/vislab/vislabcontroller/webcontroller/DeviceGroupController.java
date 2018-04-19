package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import no.ntnu.vislab.vislabcontroller.entity.DeviceGroup;
import no.ntnu.vislab.vislabcontroller.repositories.DeviceGroupRepository;
@Controller
@RequestMapping("/devicegroup")
public class DeviceGroupController {
    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @RequestMapping("/groups")
    public ResponseEntity<List<DeviceGroup>> getAll(){
        return new ResponseEntity<>(deviceGroupRepository.findAll(), HttpStatus.OK);
    }
}
