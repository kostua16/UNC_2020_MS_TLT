package nc.unc.cs.services.bank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PaymentRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentRequestId;

  @NotNull(message = "Incorrect service ID")
  @Min(value = 1L, message = "Incorrect service ID")
  @Column(nullable = false, updatable = false)
  private Long serviceId;

  @NotNull(message = "Incorrect citizen ID")
  @Min(value = 1L, message = "Incorrect citizen ID")
  @Column(nullable = false, updatable = false)
  private Long citizenId;

  @NotNull
  @Column(nullable = false)
  private Boolean status;

  @NotNull(message = "Incorrect amount")
  @Min(value = 1, message = "Incorrect amount")
  @Column(nullable = false)
  private Integer amount;

  @NotNull(message = "Incorrect tax ID")
  @Min(value = 1L, message = "Incorrect tax ID")
  @Column(nullable = false, updatable = false, unique = true)
  private Long taxId;

  @Builder
  public PaymentRequest(
      final Long paymentRequestId,
      final Long serviceId,
      final Integer amount,
      final Long citizenId,
      final Long taxId) {
    this.paymentRequestId = paymentRequestId;
    this.serviceId = serviceId;
    this.citizenId = citizenId;
    this.status = false;
    this.amount = amount;
    this.taxId = taxId;
  }
}
