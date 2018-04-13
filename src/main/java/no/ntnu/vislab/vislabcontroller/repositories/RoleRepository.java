package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.vislab.vislabcontroller.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
