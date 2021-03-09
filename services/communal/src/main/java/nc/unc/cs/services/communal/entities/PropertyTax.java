package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PropertyTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyTaxId;

    @NotNull(message = "Incorrect tax amount")
    @Min(1)
    @Column(updatable = false, nullable = false)
    private Integer taxAmount;

    @NotNull
    @Column(nullable = false)
    private Boolean isPaid;

    @NotNull
    @Column(updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @NotNull(message = "Incorrect property ID")
    @Min(1L)
    @Column(updatable = false, nullable = false)
    private Long propertyId;

    @NotNull(message = "Incorrect payment request ID")
    @Min(1L)
    @Column(updatable = false, nullable = false)
    private Long paymentRequestId;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    @Column(updatable = false, nullable = false)
    private Long citizenId;

    @Builder
    public PropertyTax(
        final Integer taxAmount,
        final Long propertyId,
        final Long paymentRequestId,
        final Long citizenId
    ) {
      this.taxAmount = taxAmount;
      this.isPaid = false;
      this.date = new Date();
      this.propertyId = propertyId;
      this.paymentRequestId = paymentRequestId;
      this.citizenId = citizenId;
    }

    public PropertyTax() {
      this.isPaid = false;
      this.date = new Date();
    }
}
