package vislab.no.ntnu.vislabcontroller.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String roleName;

    public Role() {
    }

    public Role(@NotNull String roleName) {
        this.roleName = roleName;
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
}
