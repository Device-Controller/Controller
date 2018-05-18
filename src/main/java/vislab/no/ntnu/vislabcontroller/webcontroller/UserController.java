package vislab.no.ntnu.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletRequest;

import vislab.no.ntnu.vislabcontroller.entity.Role;
import vislab.no.ntnu.vislabcontroller.entity.User;
import vislab.no.ntnu.vislabcontroller.repositories.DeviceGroupRepository;
import vislab.no.ntnu.vislabcontroller.repositories.RoleRepository;
import vislab.no.ntnu.vislabcontroller.repositories.UserRepository;
import vislab.no.ntnu.vislabcontroller.services.UserService;

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

    @Autowired
    UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/getcurrent")
    public ResponseEntity<User> getCurrent(Principal principal) {
        return new ResponseEntity<>(userRepository.findByUsername(principal.getName()), HttpStatus.OK);
    }

    @RequestMapping(value = "/getone"
            , method = RequestMethod.GET)
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
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping("/getroles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<User> add(ServletRequest form) {
        User user = parseServletRequest(form);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<User> update(ServletRequest form){
        User user = parseServletRequest(form);
        if(user != null) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/remove"
            , method = RequestMethod.DELETE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestParam("id") int id) {
        if(userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return new ResponseEntity<>("Removed user", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    private User parseServletRequest(ServletRequest form) {
        String id_string = form.getParameter("id");
        String username = form.getParameter("username");
        if((id_string == null || id_string.isEmpty()) && userRepository.findByUsername(username) != null){
            return null;
        }
        String password = form.getParameter("password");
        String email = form.getParameter("email");
        String role_string = form.getParameter("role");
        Role role = roleRepository.findByRoleName(role_string);
        if(role == null){
            role = roleRepository.findByRoleName("USER");
        }
        User user = null;
        if (id_string != null && !id_string.isEmpty()) {
            try {
                int id = Integer.parseInt(id_string);
                if(userRepository.findById(id).isPresent()){
                    user = userRepository.findById(id).get();
                }
            } catch (NumberFormatException e){
                return null;
            }
        } else {
            user = new User(username, password, email, role);
        }
        user.setUsername(username);
        user.setRole(role);
        if(password != null && !password.isEmpty()) {
            user.setPassword(encoder.encode(password));
        }
        user.setEmail(email);
        userService.save(user);
        return user;
    }

}
