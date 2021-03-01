package nc.unc.cs.services.common.clients.tax;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;

@Data
public class TaxPayment {
    @NotNull(message = "Incorrect tax ID")
    @Min(1L)
    private Long taxId;

    @NotNull
    private Date taxPaymentDate;
}
