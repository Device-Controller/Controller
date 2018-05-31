package vislab.no.ntnu.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 *
 * Represents a Theatre entity with a theatre name, list of devices in this theatre,
 * and list of device groups also in this theatre.
 */
@Entity
@Table(name = "theatre")
public class Theatre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String theatreName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Device.class)
    @JoinTable(name = "junction_theatre_device"
            , joinColumns = @JoinColumn(name = "theatre_id")
            , inverseJoinColumns = @JoinColumn(name = "device_id"))
    private List<Device> devices;

    @JsonBackReference("devicegroup_theatre")
    @OneToMany(mappedBy = "theatre")
    private List<DeviceGroup> deviceGroups;

    private Theatre() {
        this.devices = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
    }

    public Theatre(@NotNull String theatreName) {
        this();
        this.theatreName = theatreName;
    }

    public Integer getId() {
        return id;
    }

    public List<Device> getDevices() {
        return devices;
    }

    /**
     * Adds the given device to this theatre and then adds a reference back to this theatre in said device.
     * @param device the device that will be added to this theatre
     */
    public void addDevice(Device device) {
        this.devices.add(device);
        device.addTheatre(this);
    }

    /**
     * Removes the given device from this theatre and then removes the reference to this theatre from said device.
     * @param device the device that will be removed from this theatre
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.removeTheatre(this);
    }

    public void removeAllDevices() {
        for(Device d : devices) {
            d.removeTheatre(this);
        }
        this.devices = new ArrayList<>();
    }

    public List<DeviceGroup> getDeviceGroups() {
        return this.deviceGroups;
    }

    /**
     * Adds the given device group to this theatre and then adds a reference back to this theatre in said device group.
     * @param deviceGroup the device group that wll be added to this theatre
     */
    public void addDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroups.add(deviceGroup);
        deviceGroup.setTheatre(this);
    }

    public void removeDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroups.remove(deviceGroup);
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }
}
