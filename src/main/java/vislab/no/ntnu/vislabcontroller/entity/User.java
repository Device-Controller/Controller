package vislab.no.ntnu.vislabcontroller.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
 *
 * Represents a User entity with it's username, password, email, s user role, a list of device groups for this user,
 * and a default theatre assigned to this user.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonManagedReference
    @ManyToMany(mappedBy = "users")
    private List<DeviceGroup> deviceGroups;

    private Theatre defaultTheatre;

    @ManyToOne
    private Role role;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private User() {
        this.deviceGroups = new ArrayList<>();
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String email) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String email, Role role) {
        this(username, password, email);
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public List<DeviceGroup> getDeviceGroups() {
        return deviceGroups;
    }

    public void addDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroups.add(deviceGroup);
    }

    public void addDeviceGroups(List<DeviceGroup> deviceGroups) {
        this.deviceGroups.addAll(deviceGroups);
    }

    public void removeDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroups.remove(deviceGroup);
    }

    public void removeDeviceGroups(List<DeviceGroup> deviceGroups) {
        this.deviceGroups.removeAll(deviceGroups);
    }

    public Theatre getDefaultTheatre() {
        return defaultTheatre;
    }

    public void setDefaultTheatre(Theatre defaultTheatre) {
        this.defaultTheatre = defaultTheatre;
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
