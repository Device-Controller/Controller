package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class DeviceType implements Serializable {
    @Id
    @GeneratedValue
    Integer deviceTypeID;

    @OneToMany(mappedBy = "deviceType")
    List<Device> devices;

    String deviceType;

    String manufacturer;

    String model;
}
