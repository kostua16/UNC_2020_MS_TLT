package nc.unc.cs.services.common.clients.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@Builder
public class PaymentPayload {
  private Long serviceId;
  private Long citizenId;
  private Integer amount;
  private Integer taxAmount;
}
