package no.ntnu.vislab.vislabcontroller.repositories;

import no.ntnu.vislab.vislabcontroller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
