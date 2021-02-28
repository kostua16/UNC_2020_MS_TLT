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
@Builder
public class UtilityBill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long utilityBillId;

  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date date;

  @NonNull @Column(nullable = false) private Boolean isPaid;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer coldWater;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer hotWater;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer electricity;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer coldWaterAmount;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer hotWaterAmount;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer electricityAmount;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Integer utilityAmount;

  @NonNull
  @Column(nullable = false, updatable = false)
  private Long paymentRequestId;

  @NonNull @Column(nullable = false, updatable = false) private Long propertyId;

  @NonNull @Column(nullable = false, updatable = false) private Long citizenId;
}
