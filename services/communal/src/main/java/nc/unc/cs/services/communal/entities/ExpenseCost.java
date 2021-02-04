package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseCostId;

    @NonNull
    @Column(nullable = false, unique = true, length = 40)
    private String region;

    @NonNull
    @Column(nullable = false)
    private Integer coldWaterPrice;

    @NonNull
    @Column(nullable = false)
    private Integer hotWaterPrice;

    @NonNull
    @Column(nullable = false)
    private Integer electricityPrice;
}
