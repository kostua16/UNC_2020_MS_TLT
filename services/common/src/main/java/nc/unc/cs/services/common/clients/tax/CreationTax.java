package nc.unc.cs.services.common.clients.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationTax {
    private Long serviceId;
    private Long citizenId;
    private Integer taxAmount;
}
