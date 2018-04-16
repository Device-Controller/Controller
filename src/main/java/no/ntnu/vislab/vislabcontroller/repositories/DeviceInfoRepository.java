package no.ntnu.vislab.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.vislab.vislabcontroller.entity.DeviceInfo;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Integer> {
}
