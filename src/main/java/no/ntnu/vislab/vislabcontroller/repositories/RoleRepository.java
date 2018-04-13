package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.vislab.vislabcontroller.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
