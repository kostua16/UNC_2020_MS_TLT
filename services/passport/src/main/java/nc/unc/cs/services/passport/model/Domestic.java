package nc.unc.cs.services.passport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domestic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domesticId;
    private String registration;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Boolean isActive;
    private Integer series;
    private Integer number;

    public Domestic(String registration,
                    String name,
                    String surname,
                    Date dateOfBirth,
                    Boolean isActive,
                    Integer series,
                    Integer number
    ) {
        this.registration = registration;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.series = series;
        this.number = number;
    }
}