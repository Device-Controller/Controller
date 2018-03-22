package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class DGDJunction implements Serializable {
    @Id
    @GeneratedValue
    Integer dgdJunctionID;

    @ManyToOne(optional = false)
    DeviceGroup deviceGroup;

    @ManyToOne(optional = false)
    Device device;

    public DGDJunction() {
    }

    public Integer getDgdJunctionID() {
        return dgdJunctionID;
    }

    public void setDgdJunctionID(Integer dgdJunctionID) {
        this.dgdJunctionID = dgdJunctionID;
    }

    public DeviceGroup getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
