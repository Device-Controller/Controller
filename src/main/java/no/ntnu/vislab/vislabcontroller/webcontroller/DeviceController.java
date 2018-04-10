package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.vislabcontroller.dummybase.DummyBase;
import no.ntnu.vislab.vislabcontroller.dummybase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class DeviceController {

    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<List<DummyDevice>> dummyDevices(){
        return new ResponseEntity<>(new DummyBase().getList(), HttpStatus.OK);
    }

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
                                            , @RequestParam String password
                                            , @RequestParam String email) {
        User n = new User();
        n.setUsername(name);
        n.setPassword(password);
        n.setEmail(email);
        userRepository.save(n);

        return "saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
