package nc.unc.cs.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Value;

/**
 * DTO for Car entity.
 * @since 0.1.0
 */
@Table(name = "cars")
@Entity
@Value
public class Car {
    @Id
    private String number;

    public String owner;

    public Car(String number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    public Car() {
        this.number  = "0";
        this.owner = "test";
    }

    public String getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }
}
