package no.ntnu.vislab.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ThomasSTodal
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userID;

    /*@OneToMany(mappedBy = "user")
    List<UDGJunction> udgJunctions;*/

    @ManyToMany(mappedBy = "user")
    List<DeviceGroup> deviceGroups;

    @ManyToOne
    Role role;

    @NotBlank
    String username;

    @XmlTransient
    @NotBlank
    String password;

    String email;

    public User() {
        //this.udgJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
    }

    public User(Role role, String username, String password, String email) {
        //this.udgJunctions = new ArrayList<>();
        this.deviceGroups = new ArrayList<>();
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getUserID() {
        return userID;
    }

    /*public List<UDGJunction> getUdgJunctions() {
        return udgJunctions;
    }

    public void setUdgJunctions(UDGJunction udgJunctions) {
        this.udgJunctions.add(udgJunctions);
    }*/

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }

    public void setDeviceGroups(List<DeviceGroup> deviceGroups) {
        this.deviceGroups = deviceGroups;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
