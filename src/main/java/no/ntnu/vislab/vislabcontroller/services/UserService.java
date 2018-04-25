package no.ntnu.vislab.vislabcontroller.services;

import no.ntnu.vislab.vislabcontroller.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
