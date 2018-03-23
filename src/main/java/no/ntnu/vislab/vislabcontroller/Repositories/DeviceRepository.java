package no.ntnu.vislab.vislabcontroller.Repositories;

import no.ntnu.vislab.vislabcontroller.Entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {
}
