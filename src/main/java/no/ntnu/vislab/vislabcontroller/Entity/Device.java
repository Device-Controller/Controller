package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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

    int xPos;

    int yPos;

    int rotation;

    public Device() {
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public List<DGDJunction> getDgdJunctions() {
        return dgdJunctions;
    }

    public void setDgdJunctions(List<DGDJunction> dgdJunctions) {
        this.dgdJunctions = dgdJunctions;
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
