package no.ntnu.vislab.vislabcontroller.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;

@Controller
@RequestMapping("/test")
public class DeviceController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String email){
        User u = new User();
        u.setUsername(name);
        u.setEmail(email);
        repository.save(u);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return repository.findAll();
    }
}
