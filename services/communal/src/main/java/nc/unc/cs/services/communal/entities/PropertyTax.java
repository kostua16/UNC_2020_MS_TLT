package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(updatable = false, nullable = false)
    private Integer taxAmount;

    @NonNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NonNull
    @Column(updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @NonNull
    @Column(updatable = false, nullable = false)
    private Long propertyId;

    @NonNull
    @Column(updatable = false, nullable = false)
    private Long citizenId;
}
