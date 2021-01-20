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
public class PropertyTax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyTaxId;

    @NonNull
    @Column(nullable = false)
    private Integer taxAmount;

    @NonNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NonNull
    @Column(nullable = false)
    private Date date;

    @NonNull
    @Column(nullable = false)
    private Long propertyId;

    @NonNull
    @Column(nullable = false)
    private Long citizenId;
}
