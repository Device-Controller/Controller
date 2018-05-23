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
 *
 * This is a Spring Controller, it is responsible for fetching, adding, updating and removing Users
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

    /**
     *
     * @return List of all Users in the database
     */
    @RequestMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Looks up and returns the User object
     * @param principal Currently signed in user
     * @return The User object for the currently signed in user.
     */
    @RequestMapping("/getcurrent")
    public ResponseEntity<User> getCurrent(Principal principal) {
        return new ResponseEntity<>(userRepository.findByUsername(principal.getName()), HttpStatus.OK);
    }

    /**
     * Looks up a user according to one of the Parameters defined below. Always looks up by id if id is provided,
     * if no id it looks up by username, if no username it looks up by email.
     * @param id User id
     * @param username Username
     * @param email User email
     * @return The found User, null if invalid parameter or no parameter
     */
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

    /**
     *
     * @return ResponseEntity with all Roles stored in roleRepository
     */
    @RequestMapping("/getroles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Converts form data to a User object
     * @param request Form data
     * @return ResponseEntity with the created user, or BAD_REQUEST if invalid form data
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<User> add(ServletRequest request) {
        User user = parseRequest(request);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Updates a User object with the provided form data
     * @param request Form data
     * @return ResponseEntity with the updated user, or BAD_REQUEST if invalid form data
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update"
            , method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<User> update(ServletRequest request){
        User user = parseRequest(request);
        if(user != null) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Removes a user according to the given id
     * @param id User id
     * @return ResponseEntity with confirmation that a user has been removed, or BAD_REQUEST if invalid id
     */
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

    /**
     * Attempts to parse form data into a User object
     * @param request Form data
     * @return The new User object, or null if invalid form data
     */
    private User parseRequest(ServletRequest request) {
        String id_string = request.getParameter("id");
        String username = request.getParameter("username");
        if((id_string == null || id_string.isEmpty()) && userRepository.findByUsername(username) != null){
            return null;
        }
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role_string = request.getParameter("role");
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
