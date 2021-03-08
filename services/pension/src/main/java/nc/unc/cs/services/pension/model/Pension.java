package nc.unc.cs.services.pension.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pension {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pensionId;
  private Integer amountOfPension;
  private Long citizenId;
  private Integer pensionExperience;
}
