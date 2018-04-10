package no.ntnu.vislab.vislabcontroller.repositories;

import no.ntnu.vislab.vislabcontroller.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
