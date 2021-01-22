package nc.unc.cs.services.passport.integration.bank_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Long paymentRequestId;
    private Long serviceId;
    private Long citizenId;
    private Boolean status;
    private Integer amount;
    private Long taxId;
}
