package no.ntnu.vislab.vislabcontroller.Entity;

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

    @OneToMany(mappedBy = "device")
    List<DGDJunction> dgdJunctions;

    @ManyToOne(optional = false)
    DeviceType deviceType;

    String ipAdress;

    int port;

    int xPos;

    int yPos;

    int rotation;

    public Device() {
        this.dgdJunctions = new ArrayList<>();
    }

    public Device(DeviceType deviceType, String ipAdress, int port, int xPos, int yPos, int rotation) {
        this.dgdJunctions = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAdress = ipAdress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
    }

    public Device(DeviceType deviceType, String ipAdress, int port, int xPos, int yPos) {
        this.dgdJunctions = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAdress = ipAdress;
        this.port = port;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Device(DeviceType deviceType, String ipAdress, int port) {
        this.dgdJunctions = new ArrayList<>();
        this.deviceType = deviceType;
        this.ipAdress = ipAdress;
        this.port = port;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public List<DGDJunction> getDgdJunctions() {
        return dgdJunctions;
    }

    public void setDgdJunctions(DGDJunction dgdJunction) {
        this.dgdJunctions.add(dgdJunction);
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
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
