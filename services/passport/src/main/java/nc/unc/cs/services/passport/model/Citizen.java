package nc.unc.cs.services.passport.model;

import java.util.Date;
import lombok.Data;

@Data
public class Citizen {
  private String name;
  private String surname;
  private Date dateOfBirth;
  private String registration;
  private Long citizenId;
}
