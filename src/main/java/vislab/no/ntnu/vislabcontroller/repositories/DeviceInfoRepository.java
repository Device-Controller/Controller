package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.DeviceInfo;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Integer> {
}
