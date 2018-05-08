package vislab.no.ntnu.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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
    }

    public Device(@NotNull String ipAddress, @NotNull int port, int xPos, int yPos, int rotation, @NotNull DeviceInfo deviceInfo) {
        this();
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

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void addTheatre(Theatre theatre) {
        this.theatres.add(theatre);
    }

    public void removeTheatre(Theatre theatre) {
        this.theatres.remove(theatre);
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
