package nc.unc.cs.services.common.clients.bank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Incorrect payment request ID")
    @Min(1L)
    private Long paymentRequestId;

    @NotNull(message = "Incorrect service ID")
    @Min(1L)
    private Long serviceId;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    private Long citizenId;

    @NotNull
    private Boolean status;

    @NotNull(message = "Incorrect service ID")
    @Min(1)
    private Integer amount;

    @NotNull(message = "Incorrect tax ID")
    @Min(1L)
    private Long taxId;
}
