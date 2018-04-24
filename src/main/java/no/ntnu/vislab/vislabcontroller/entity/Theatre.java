package no.ntnu.vislab.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@Entity
@Table(name = "theatre")
public class Theatre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String theatreName;

    @JsonManagedReference("device_theatre")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "junction_theatre_device"
            , joinColumns = @JoinColumn(name = "theatre_id")
            , inverseJoinColumns = @JoinColumn(name = "device_id"))
    private List<Device> devices;

    @JsonManagedReference("devicegroup_theatre")
    /*@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})*/
    @OneToMany(mappedBy = "theatre")
    private List<DeviceGroup> deviceGroups;

    public Theatre() {
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
        device.getTheatres().add(this);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
        device.getTheatres().remove(this);
    }

    public List<DeviceGroup> getDeviceGroups() {
        return this.deviceGroups;
    }

    public void addDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroups.add(deviceGroup);/*
        deviceGroup.setTheatre(this);*/
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
