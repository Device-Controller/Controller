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
}
