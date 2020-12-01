package nc.unc.cs.entities;


/**
 * DTO for Car entity.
 * @since 0.1.0
 */
public class Car {
    private final String number;

    public final String owner;

    public Car(String number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }
}
