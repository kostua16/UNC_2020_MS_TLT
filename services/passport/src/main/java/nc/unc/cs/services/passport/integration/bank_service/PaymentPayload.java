package nc.unc.cs.services.passport.integration.bank_service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentPayload {
    private Long serviceId;
    private Long citizenId;
    private Integer amount;
    private Integer taxAmount;
}
