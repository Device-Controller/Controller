package vislab.no.ntnu.vislabcontroller.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author KristofferRogne
 *
 * Represents a DeviceType entity with a type name (eg. "Projector", "Sound System", "Computer" etc.)
 */
@Entity
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String type;

    public DeviceType(@NotNull String type) {
        this.type = type;
    }

    public DeviceType() {
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
