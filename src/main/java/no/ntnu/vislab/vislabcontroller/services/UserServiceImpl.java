package no.ntnu.vislab.vislabcontroller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.vislab.vislabcontroller.entity.User;
import no.ntnu.vislab.vislabcontroller.repositories.RoleRepository;
import no.ntnu.vislab.vislabcontroller.repositories.UserRepository;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(roleRepository.findAll().get(0));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
