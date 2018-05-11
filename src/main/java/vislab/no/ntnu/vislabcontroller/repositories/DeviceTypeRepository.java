package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
    DeviceType findByType(String type);
}
