package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.vislab.vislabcontroller.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
