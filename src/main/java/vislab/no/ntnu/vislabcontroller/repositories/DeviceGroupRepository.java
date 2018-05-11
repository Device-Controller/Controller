package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;

import java.util.List;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
    //List<DeviceGroup> findByDefaultDGroup(boolean isDefaultDGroup);
}
