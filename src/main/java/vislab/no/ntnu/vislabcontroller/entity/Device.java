package vislab.no.ntnu.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 */
@Entity
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String defaultName;

    private Map<Theatre, String> deviceName;

    @JsonIgnore
    @ManyToMany(mappedBy = "devices")
    private List<DeviceGroup> deviceGroups;

    @JsonBackReference("device_theatre")
    @ManyToMany(mappedBy = "devices")
    private List<Theatre> theatres;

    @ManyToOne(optional = false)
    @NotNull
    private DeviceInfo deviceInfo;

    @NotNull
    private String ipAddress;

    @NotNull
    private int port;

    private int xPos;

    private int yPos;

    private int rotation;

    public Device() {
        this.deviceGroups = new ArrayList<>();
        this.theatres = new ArrayList<>();
        this.deviceName = new HashMap<>();
    }

    public Device(@NotNull DeviceInfo deviceInfo, @NotNull String ipAddress
            , @NotNull int port, int xPos, int yPos, int rotation) {
        this();
        this.deviceInfo = deviceInfo;
        this.ipAddress = ipAddress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
    }

    public Device(String defaultName, @NotNull DeviceInfo deviceInfo, @NotNull String ipAddress
            , @NotNull int port, int xPos, int yPos, int rotation) {
        this(deviceInfo, ipAddress, port, xPos, yPos, rotation);
        this.defaultName = defaultName;
    }

    public Integer getId() {
        return id;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDeviceName(Theatre theatre) {
        return this.deviceName.get(theatre);
    }

    public void mapDeviceName(Theatre theatre, String deviceName) {
        this.deviceName.put(theatre, deviceName);
    }

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void addTheatre(Theatre theatre) {
        this.theatres.add(theatre);
        this.mapDeviceName(theatre, this.defaultName);
    }

    public void removeTheatre(Theatre theatre) {
        this.theatres.remove(theatre);
        this.deviceName.remove(theatre);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return getId() == device.getId() &&
                getPort() == device.getPort() &&
                getxPos() == device.getxPos() &&
                getyPos() == device.getyPos() &&
                getRotation() == device.getRotation() &&
                Objects.equals(getDeviceInfo(), device.getDeviceInfo()) &&
                Objects.equals(getIpAddress(), device.getIpAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getDeviceInfo(), getIpAddress(), getPort(), getxPos(), getyPos(), getRotation());
    }
}
