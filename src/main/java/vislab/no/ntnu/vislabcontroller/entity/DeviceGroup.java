package vislab.no.ntnu.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 */
@Entity
@Table(name="devicegroup")
public class DeviceGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String groupName;

    @JsonManagedReference("user_devicegroup")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "junction_devicegroup_user"
            , joinColumns = @JoinColumn(name = "devicegroup_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")) //this creates the junction in a table named "junction_devicegroup_user"
    private List<User> users;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "junction_devicegroup_device"
            , joinColumns = @JoinColumn(name = "devicegroup_id")
            , inverseJoinColumns = @JoinColumn(name = "device_id")) //this creates the junction in a table named "junction_devicegroup_device"
    private List<Device> devices;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Theatre theatre;

    @NotNull
    private boolean isDefaultDGroup;

    public DeviceGroup() {
        this.users = new ArrayList<>();
        this.devices = new ArrayList<>();
    }

    public DeviceGroup(@NotNull String groupName, @NotNull Theatre theatre) {
        this();
        this.groupName = groupName;
        this.theatre = theatre;
        this.isDefaultDGroup = false;
    }

    public DeviceGroup(@NotNull String groupName, @NotNull Theatre theatre, @NotNull boolean isDefaultDGroup) {
        this();
        this.groupName = groupName;
        this.theatre = theatre;
        this.isDefaultDGroup = isDefaultDGroup;
    }

    public Integer getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getDeviceGroups().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getDeviceGroups().remove(this);
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
        device.getDeviceGroups().add(this);
    }
    public void removeDevice(Device device){
        this.devices.remove(device);
        device.getDeviceGroups().remove(this);
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isDefaultDGroup() {
        return isDefaultDGroup;
    }

    public void setDefaultDGroup(boolean defaultDGroup) {
        isDefaultDGroup = defaultDGroup;
    }
}
