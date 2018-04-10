package no.ntnu.vislab.vislabcontroller.repositories;

import no.ntnu.vislab.vislabcontroller.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
}
