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
    private int id;

    @NotNull
    private String roleName;

    @NotNull
    private boolean canAddDevice;

    @NotNull
    private boolean canCreateUser;

    @NotNull
    private boolean canRemoveDevice;

    @NotNull
    private boolean canRemoveUser;
    public Role() {
    }

    public Role(@NotNull String roleName) {
        this.roleName = roleName;
        this.canAddDevice = false;
        this.canCreateUser = false;
        this.canRemoveDevice = false;
        this.canRemoveUser = false;
    }

    public Role(@NotNull String roleName, @NotNull boolean canAddDevice, @NotNull boolean canCreateUser,
                @NotNull boolean canRemoveDevice, @NotNull boolean canRemoveUser) {
        this.roleName = roleName;
        this.canAddDevice = canAddDevice;
        this.canCreateUser = canCreateUser;
        this.canRemoveDevice = canRemoveDevice;
        this.canRemoveUser = canRemoveUser;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean canAddDevice() {
        return canAddDevice;
    }

    public void setAddDevicePrivilage(boolean canAddDevice) {
        this.canAddDevice = canAddDevice;
    }

    public boolean canCreateUser() {
        return canCreateUser;
    }

    public void setCreateUserPrivelage(boolean canCreateUser) {
        this.canCreateUser = canCreateUser;
    }

    public boolean canRemoveDevice() {
        return canRemoveDevice;
    }

    public void setRemoveDevicePrivelage(boolean canRemoveDevice) {
        this.canRemoveDevice = canRemoveDevice;
    }

    public boolean canRemoveUser() {
        return canRemoveUser;
    }

    public void setRemoveUserPrivelage(boolean canRemoveUser) {
        this.canRemoveUser = canRemoveUser;
    }
}
