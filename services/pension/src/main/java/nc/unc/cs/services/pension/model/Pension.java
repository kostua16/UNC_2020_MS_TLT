package nc.unc.cs.services.pension.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Long domesticId;

}
