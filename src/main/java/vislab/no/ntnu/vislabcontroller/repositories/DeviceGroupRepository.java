package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
}
