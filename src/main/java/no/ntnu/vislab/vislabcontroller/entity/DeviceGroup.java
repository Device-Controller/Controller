package no.ntnu.vislab.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 */
@Entity
@Table(name="devicegroup")
public class DeviceGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotNull
    private String groupName;
    @JsonManagedReference("user_devicegroup")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "junction_devicegroup_user", joinColumns = @JoinColumn(name = "devicegroup_id"), inverseJoinColumns = @JoinColumn(name = "user_id")) //this creates the junction in a table named "junction_devicegroup_user"
    private List<User> users;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "junction_devicegroup_device", joinColumns = @JoinColumn(name = "devicegroup_id"), inverseJoinColumns = @JoinColumn(name = "device_id"))//this creates the junction in a table named "junction_devicegroup_device"
    List<Device> devices;

    public DeviceGroup() {
        this.users = new ArrayList<>();
        this.devices = new ArrayList<>();
    }

    public DeviceGroup(@NotNull String groupName) {
        this();
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
