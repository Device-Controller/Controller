package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import no.ntnu.vislab.vislabcontroller.Security.UserValidator;
import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.services.SecurityService;
import no.ntnu.vislab.vislabcontroller.services.UserService;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator validator;
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String registration(@Valid @RequestBody User userForm, BindingResult bindingResult) {

        validator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/";
    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String login(@Valid @RequestBody User userForm, BindingResult bindingResult) {
//
//        validator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "404";
//        }
//        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
//
//        return "redirect:/";
//    }
}
