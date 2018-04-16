package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.vislab.vislabcontroller.entity.DeviceGroup;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
}
