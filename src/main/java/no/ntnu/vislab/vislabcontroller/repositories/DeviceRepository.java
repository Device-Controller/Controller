package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.vislab.vislabcontroller.entity.Device;

public interface DeviceRepository extends CrudRepository<Device, Long> {
}
