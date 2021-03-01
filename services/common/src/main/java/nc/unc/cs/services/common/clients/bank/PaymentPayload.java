package nc.unc.cs.services.common.clients.bank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Incorrect service ID")
    @Min(1L)
    private Long serviceId;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    private Long citizenId;

    @NotNull(message = "Incorrect amount")
    @Min(1)
    private Integer amount;

    @NotNull(message = "Incorrect taxAmount")
    @Min(1)
    private Integer taxAmount;
}
