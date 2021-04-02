package nc.unc.cs.services.common.clients.passport;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Citizen {
  private String name;
  private String surname;
  private Date dateOfBirth;
  private String registration;
  private Long citizenId;
}
