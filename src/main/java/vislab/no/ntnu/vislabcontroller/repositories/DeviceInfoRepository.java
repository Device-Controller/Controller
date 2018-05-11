package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.DeviceInfo;
import vislab.no.ntnu.vislabcontroller.entity.DeviceType;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Integer> {
    DeviceInfo findByManufacturerAndModelAndDeviceType(String manufacturer, String Model, DeviceType type);
}
