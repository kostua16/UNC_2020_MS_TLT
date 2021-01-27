package nc.unc.cs.services.common.clients.tax;

import java.util.Date;
import lombok.Data;

@Data
public class TaxPayment {
    private Long taxId;
    private Date taxPaymentDate;
}
