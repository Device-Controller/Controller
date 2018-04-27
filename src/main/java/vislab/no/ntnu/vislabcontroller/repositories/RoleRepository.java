package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
