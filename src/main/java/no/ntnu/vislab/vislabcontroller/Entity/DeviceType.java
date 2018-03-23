package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class DeviceType implements Serializable {
    @Id
    @GeneratedValue
    Integer deviceTypeID;

    @OneToMany(mappedBy = "deviceType")
    List<Device> devices;

    String deviceType;

    String manufacturer;

    String model;

    public DeviceType() {
        this.devices = new ArrayList<>();
    }

    public DeviceType(String deviceType, String manufacturer, String model) {
        this.devices = new ArrayList<>();
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Integer getDeviceTypeID() {
        return deviceTypeID;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(Device devices) {
        this.devices.add(devices);
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
