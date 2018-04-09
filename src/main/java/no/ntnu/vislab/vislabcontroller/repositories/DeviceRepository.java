package no.ntnu.vislab.vislabcontroller.repositories;

import no.ntnu.vislab.vislabcontroller.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {
}
