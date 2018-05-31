package vislab.no.ntnu.vislabcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import vislab.no.ntnu.vislabcontroller.entity.Device;
import vislab.no.ntnu.vislabcontroller.entity.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    Theatre findByTheatreName(String theatreName);

    List<Theatre> findAllByDevices(List<Device> devices);
}
