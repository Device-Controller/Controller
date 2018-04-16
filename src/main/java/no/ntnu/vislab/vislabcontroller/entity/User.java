package no.ntnu.vislab.vislabcontroller.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author ThomasSTodal
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToMany(mappedBy = "users")
    private List<DeviceGroup> deviceGroups;

    @ManyToOne
    private Role role;

    private String username;

    private String password;

    private String email;

    public User(String username, String password, String email, Role role) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.deviceGroups = new ArrayList<>();
    }

    public User() {
        this.deviceGroups = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
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
