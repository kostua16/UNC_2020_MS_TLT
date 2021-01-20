package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @NonNull
    @Column(nullable = false)
    private Date date;

    @NonNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NonNull
    @Column(nullable = false)
    private Integer coldWater;

    @NonNull
    @Column(nullable = false)
    private Integer hotWater;

    @NonNull
    @Column(nullable = false)
    private Integer electricity;

    @NonNull
    @Column(nullable = false)
    private Integer coldWaterAmount;

    @NonNull
    @Column(nullable = false)
    private Integer hotWaterAmount;

    @NonNull
    @Column(nullable = false)
    private Integer electricityAmount;

    @NonNull
    @Column(nullable = false)
    private Long propertyId;

    @NonNull
    @Column(nullable = false)
    private Long citizenId;
}
