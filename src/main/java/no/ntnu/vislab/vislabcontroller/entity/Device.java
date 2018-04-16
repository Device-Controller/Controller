package no.ntnu.vislab.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author ThomasSTodal
 */
@Entity
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonManagedReference
    @ManyToMany(mappedBy = "devices")
    private List<DeviceGroup> deviceGroups;

    @JsonBackReference
    @ManyToOne(optional = false)
    private DeviceInfo deviceInfo;

    private String ipAddress;

    private int port;

    private int xPos;

    private int yPos;

    private int rotation;

    public Device() {
        this.deviceGroups = new ArrayList<>();
    }

    public Device(String ipAddress, int port, int xPos, int yPos, int rotation, DeviceInfo deviceInfo) {
        this.deviceGroups = new ArrayList<>();
        this.deviceInfo = deviceInfo;
        this.ipAddress = ipAddress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
    }

    public Integer getId() {
        return id;
    }

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }
    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
