package vislab.no.ntnu.vislabcontroller.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
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

    @JsonManagedReference("devicegroup_theatre")
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

    public void addDevice(Device device) {
        this.devices.add(device);
        device.addTheatre(this);
    }

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
