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
import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.Role;
import vislab.no.ntnu.vislabcontroller.entity.User;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.RoleRepository;
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
    @Autowired
    RoleRepository roleRepository;

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
        List<DeviceGroup> dgs = deviceGroupRepository.findAll();
        for(DeviceGroup dg : dgs) {
            if(!dg.isDefaultDGroup())
                dgs.remove(dg);
        }
        user.addDeviceGroups(dgs);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateusername"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUsername(@RequestParam("id") Integer id
            , @RequestParam("username") String username) {
        User u = userRepository.findById(id).get();
        u.setUsername(username);
        return new ResponseEntity<>(userRepository.save(u), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateemail"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateEmail(@RequestParam("id") Integer id
            , @RequestParam("email") String email) {
        User u = userRepository.findById(id).get();
        u.setEmail(email);
        return new ResponseEntity<>(userRepository.save(u), HttpStatus.OK);
    }

    @RequestMapping(value = "/updaterole"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateRole(@RequestParam("id") Integer id
            , @RequestParam("rolename") String roleName) {
        User u = userRepository.findById(id).get();
        Role r = roleRepository.findByRoleName(roleName);
        u.setRole(r);
        return new ResponseEntity<>(userRepository.save(u), HttpStatus.OK);
    }

    @RequestMapping(value = "/addgroup"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addGroup(@RequestParam("id") Integer id
            , @RequestParam("rolename") String roleName) {
        User u = userRepository.findById(id).get();
        Role r = roleRepository.findByRoleName(roleName);
        u.setRole(r);
        return new ResponseEntity<>(userRepository.save(u), HttpStatus.OK);
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
