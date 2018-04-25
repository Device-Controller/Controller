package no.ntnu.vislab.vislabcontroller.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author ThomasSTodal
 */
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String roleName;

    @NotNull
    private boolean addDevice;

    @NotNull
    private boolean createUser;

    @NotNull
    private boolean removeDevice;

    @NotNull
    private boolean removeUser;

    public Role() {
    }

    public Role(@NotNull String roleName, @NotNull boolean addDevice, @NotNull boolean createUser, @NotNull boolean removeDevice, @NotNull boolean removeUser) {
        this.roleName = roleName;
        this.addDevice = addDevice;
        this.createUser = createUser;
        this.removeDevice = removeDevice;
        this.removeUser = removeUser;
    }

    public Integer getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isAddDevice() {
        return addDevice;
    }

    public void setAddDevice(boolean addDevice) {
        this.addDevice = addDevice;
    }

    public boolean isCreateUser() {
        return createUser;
    }

    public void setCreateUser(boolean createUser) {
        this.createUser = createUser;
    }

    public boolean isRemoveDevice() {
        return removeDevice;
    }

    public void setRemoveDevice(boolean removeDevice) {
        this.removeDevice = removeDevice;
    }

    public boolean isRemoveUser() {
        return removeUser;
    }

    public void setRemoveUser(boolean removeUser) {
        this.removeUser = removeUser;
    }
}
