package nc.unc.cs.services.bank.controllers.payloads.responses;

import java.util.Date;
import lombok.Data;

@Data
public class TaxPayment {
    private Long taxId;
    private Date taxPaymentDate;
}
