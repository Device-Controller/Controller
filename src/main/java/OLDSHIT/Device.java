package OLDSHIT;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Device implements Serializable {
    @Id
    @GeneratedValue
    Integer deviceID;

    /*@OneToMany(mappedBy = "device")
    List<DGDJunction> dgdJunctions;*/

    @ManyToMany(mappedBy = "devices")
    List<DeviceGroup> deviceGroups;

    @ManyToOne(optional = false)
    DeviceType deviceType;

    String ipAddress;

    int port;

    int xPos;

    int yPos;

    int rotation;

    public Device() {
        //this.dgdJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
    }

    public Device(DeviceType deviceType, String ipAddress, int port, int xPos, int yPos, int rotation) {
        //this.dgdJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
    }

    public Device(DeviceType deviceType, String ipAddress, int port, int xPos, int yPos) {
        //this.dgdJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Device(DeviceType deviceType, String ipAddress, int port) {
        //this.dgdJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    /*public List<DGDJunction> getDgdJunctions() {
        return dgdJunctions;
    }

    public void setDgdJunctions(DGDJunction dgdJunction) {
        this.dgdJunctions.add(dgdJunction);
    }*/

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }

    public void setDeviceGroups(List<DeviceGroup> deviceGroups) {
        this.deviceGroups = deviceGroups;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
