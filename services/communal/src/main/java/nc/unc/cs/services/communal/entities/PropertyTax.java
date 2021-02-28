package nc.unc.cs.services.communal.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class PropertyTax {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long propertyTaxId;

  @NonNull
  @Column(updatable = false, nullable = false)
  private Integer taxAmount;

  @NonNull @Column(nullable = false) private Boolean isPaid;

  @NonNull
  @Column(updatable = false, nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date date;

  @NonNull @Column(updatable = false, nullable = false) private Long propertyId;

  @NonNull
  @Column(updatable = false, nullable = false)
  private Long paymentRequestId;

  @NonNull @Column(updatable = false, nullable = false) private Long citizenId;
}
