package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.vislab.vislabcontroller.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
