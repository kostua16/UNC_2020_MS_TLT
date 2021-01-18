package nc.unc.cs.services.passport.integration.tax_service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdInfo {
    private Long citizenId;
    private Long serviceId;

}
