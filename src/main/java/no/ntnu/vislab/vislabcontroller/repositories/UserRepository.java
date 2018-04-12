package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.vislab.vislabcontroller.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
