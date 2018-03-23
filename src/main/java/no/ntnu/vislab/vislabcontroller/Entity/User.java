package no.ntnu.vislab.vislabcontroller.Entity;

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
public class User implements Serializable {
    @Id
    @GeneratedValue
    Integer userID;

    @OneToMany(mappedBy = "user")
    List<UDGJunction> udgJunctions;

    @ManyToOne(optional = true)
    Role role;

    String username;

    @XmlTransient
    String password;

    String email;

    public User() {
        this.udgJunctions = new ArrayList<>();
    }

    public User(Role role, String username, String password, String email) {
        this.udgJunctions = new ArrayList<>();
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getUserID() {
        return userID;
    }

    public List<UDGJunction> getUdgJunctions() {
        return udgJunctions;
    }

    public void setUdgJunctions(UDGJunction udgJunctions) {
        this.udgJunctions.add(udgJunctions);
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
