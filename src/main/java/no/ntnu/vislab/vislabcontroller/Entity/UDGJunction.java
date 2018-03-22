package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class UDGJunction implements Serializable {
    @Id
    @GeneratedValue
    Integer udgJunctionID;

    @ManyToOne(optional = false)
    User user;

    @ManyToOne(optional = false)
    DeviceGroup deviceGroup;
}
