package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vislab.no.ntnu.vislabcontroller.entity.User;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author ThomasSTodal
 */
@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @RequestMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getone"
            , method = RequestMethod.POST)
    public ResponseEntity<User> getOne(@RequestParam("id") Optional<Integer> id
            , @RequestParam("username") Optional<String> username
            , @RequestParam("email") Optional<String> email) {
        User u = null;
        if (id.isPresent()) {
            if (userRepository.findById(id.get()).isPresent())
                u = userRepository.findById(id.get()).get();
        } else if (username.isPresent()) {
            u = userRepository.findByUsername(username.get());
        } else if (email.isPresent()) {
            u = userRepository.findByEmail(email.get());
        } //TODO throw exception here?
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping(value = "/addone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addOne(@RequestBody User user) {
        user.addDeviceGroups(deviceGroupRepository.findByDefaultDGroup(true)); //TODO is this even ok?
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/removeone"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOne(@RequestBody User user) {
        userRepository.delete(user);
        return new ResponseEntity<>("Removed user: "
                + user.getUsername(), HttpStatus.OK);
    }

    @RequestMapping(value = "/removelist"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeList(@RequestBody User[] userArray) {
        List<User> users = new ArrayList<>(Arrays.asList(userArray));
        userRepository.deleteAll(users);
        return new ResponseEntity<>("Removed users", HttpStatus.OK);
    }

    @RequestMapping("/removeall")
    public ResponseEntity<String> removeAll() {
        userRepository.deleteAll();
        return new ResponseEntity<>("Removed all users", HttpStatus.OK);
    }
}
