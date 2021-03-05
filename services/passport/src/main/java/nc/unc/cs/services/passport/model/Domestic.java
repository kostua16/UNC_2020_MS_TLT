package nc.unc.cs.services.passport.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
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
    private Long citizenId;

}