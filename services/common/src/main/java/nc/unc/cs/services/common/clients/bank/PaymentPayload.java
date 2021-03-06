package nc.unc.cs.services.common.clients.bank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentPayload {
  /** Service ID. */
  @NotNull(message = "Incorrect service ID")
  @Min(value = 1L, message = "Incorrect service ID")
  private Long serviceId;

  /** Citizen ID. */
  @NotNull(message = "Incorrect citizen ID")
  @Min(value = 1L, message = "Incorrect citizen ID")
  private Long citizenId;

  /** Service cost. */
  @NotNull(message = "Incorrect amount")
  @Min(value = 1, message = "Incorrect amount")
  private Integer amount;

  /** Tax amount. */
  @NotNull(message = "Incorrect taxAmount")
  @Min(value = 1, message = "Incorrect tax ID")
  private Integer taxAmount;
}
