package no.ntnu.vislab.vislabcontroller.webcontroller;

import no.ntnu.vislab.vislabcontroller.dummybase.DummyBase;
import no.ntnu.vislab.vislabcontroller.dummybase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.exception.ResourceNotFoundException;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/test")
public class DeviceController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public User addNewUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userID) {
        User user = userRepository.findOne(userID);
        if(user == null) {
            throw new ResourceNotFoundException("User", "id", userID);
        }
        return user;
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long userID,
                           @Valid @RequestBody User userDetails) {
        User user = userRepository.findOne(userID);
        if(user == null) {
            throw new ResourceNotFoundException("User", "id", userID);
        }

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setDeviceGroups(userDetails.getDeviceGroups());
        user.setRole(userDetails.getRole());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userID) {
        User user = userRepository.findOne(userID);
        if(user == null) {
            throw new ResourceNotFoundException("User", "id", userID);
        }
        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

    /*@RequestMapping("/testDummy")
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
    }*/
}
