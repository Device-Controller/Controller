package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import no.ntnu.vislab.vislabcontroller.Security.UserValidator;
import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;
import no.ntnu.vislab.vislabcontroller.services.SecurityService;
import no.ntnu.vislab.vislabcontroller.services.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator validator;

    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/login")
    public String registration(@Valid @RequestBody User user, BindingResult bindingResult) {
        User userForm = userRepository.findByUsername(user.getUsername());

        validator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/";
    }
//
//    @RequestMapping("/login")
//    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
//        System.out.println(username);
//        System.out.println(password);
//        User u = userRepository.findByUsernameAndPassword(username, password);
//        System.out.println(u);
//        if (u != null) {
//            return new ResponseEntity<>(u, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new User(), HttpStatus.OK);
//    }
}
