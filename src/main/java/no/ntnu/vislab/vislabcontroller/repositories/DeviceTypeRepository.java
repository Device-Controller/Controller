package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.vislab.vislabcontroller.entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
}
