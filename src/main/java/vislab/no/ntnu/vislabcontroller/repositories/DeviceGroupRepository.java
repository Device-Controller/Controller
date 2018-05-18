package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import vislab.no.ntnu.vislabcontroller.entity.DeviceGroup;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
    List<DeviceGroup> findAllByTheatre(Theatre theatre);
    //List<DeviceGroup> findByDefaultDGroup(boolean isDefaultDGroup);
}
