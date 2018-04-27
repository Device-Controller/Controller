package vislab.no.ntnu.vislabcontroller.services;

import vislab.no.ntnu.vislabcontroller.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
