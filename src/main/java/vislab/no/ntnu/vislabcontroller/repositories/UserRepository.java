package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vislab.no.ntnu.vislabcontroller.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
