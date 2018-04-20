package no.ntnu.vislab.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @JsonBackReference("user_devicegroup")
    @ManyToMany(mappedBy = "users")
    private List<DeviceGroup> deviceGroups;

    @ManyToOne
    private Role role;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    public User(@NotNull String username, @NotNull String password, @NotNull String email, Role role) {
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
