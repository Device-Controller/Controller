package OLDSHIT;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.entity.User;

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

    /*@OneToMany(mappedBy = "deviceGroup")
    List<UDGJunction> udgJunctions;*/

    @ManyToMany(mappedBy = "deviceGroups")
    List<User> users;

    /*@OneToMany(mappedBy = "deviceGroup")
    List<DGDJunction> dgdJunctions;*/

    @ManyToMany(mappedBy = "deviceGroups")
    List<Device> devices;

    public DeviceGroup() {
        //this.udgJunctions = new ArrayList<>();
        //this.dgdJunctions = new ArrayList<>();
        this.users = new ArrayList<>();
        this.devices = new ArrayList<>();
    }

    public Integer getDeviceGroupID() {
        return deviceGroupID;
    }

    /*public List<UDGJunction> getUdgJunctions() {
        return udgJunctions;
    }

    public void setUdgJunctions(UDGJunction udgJunctions) {
        this.udgJunctions.add(udgJunctions);
    }*/

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
/*public List<DGDJunction> getDgdJunctions() {
        return dgdJunctions;
    }

    public void setDgdJunctions(DGDJunction dgdJunctions) {
        this.dgdJunctions.add(dgdJunctions);
    }*/

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
