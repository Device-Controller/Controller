package no.ntnu.vislab.vislabcontroller.Entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User {
    @Id
    @GeneratedValue
    Integer user_ID;

    @OneToMany(mappedBy = "user")
    List<UDGJunction> udgJunction_ID;

    @ManyToOne(optional = true)
    Role role;

    String username;

    @XmlTransient
    String password;

    String email;
}
