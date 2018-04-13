package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.vislab.vislabcontroller.entity.DeviceType;

public interface DeviceTypeRepository extends CrudRepository<DeviceType, Long> {
}
