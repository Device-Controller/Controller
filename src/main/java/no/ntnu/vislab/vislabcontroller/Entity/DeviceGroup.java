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
public class DeviceGroup implements Serializable {
    @Id
    @GeneratedValue
    Integer deviceGroupID;

    @OneToMany(mappedBy = "deviceGroup")
    List<UDGJunction> udgJunctions;

    @OneToMany(mappedBy = "deviceGroup")
    List<DGDJunction> dgdJunctions;

    public DeviceGroup() {
    }

    public Integer getDeviceGroupID() {
        return deviceGroupID;
    }

    public void setDeviceGroupID(Integer deviceGroupID) {
        this.deviceGroupID = deviceGroupID;
    }

    public List<UDGJunction> getUdgJunctions() {
        return udgJunctions;
    }

    public void setUdgJunctions(List<UDGJunction> udgJunctions) {
        this.udgJunctions = udgJunctions;
    }

    public List<DGDJunction> getDgdJunctions() {
        return dgdJunctions;
    }

    public void setDgdJunctions(List<DGDJunction> dgdJunctions) {
        this.dgdJunctions = dgdJunctions;
    }
}
