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
public class International {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long internationalId;

  private Boolean locked;
  private String name;
  private String surname;
  private Date dateOfBirth;
  private Boolean isActive;
  private Long citizenId;
}
