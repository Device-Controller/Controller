package OLDSHIT;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import no.ntnu.vislab.vislabcontroller.entity.User;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class UDGJunction implements Serializable {
    @Id
    @GeneratedValue
    Integer udgJunctionID;

    @ManyToOne(optional = false)
    User user;

    @ManyToOne(optional = false)
    DeviceGroup deviceGroup;

    public UDGJunction() {
    }

    public UDGJunction(User user, DeviceGroup deviceGroup) {
        this.user = user;
        this.deviceGroup = deviceGroup;
    }

    public Integer getUdgJunctionID() {
        return udgJunctionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeviceGroup getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }
}
