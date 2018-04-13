package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.vislab.vislabcontroller.entity.DeviceInfo;

public interface DeviceInfoRepository extends CrudRepository<DeviceInfo, Long> {
}
