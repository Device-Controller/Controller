package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
