package nc.unc.cs.services.communal.controllers.payloads;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IdRequest {
  @NotNull(message = "Incorrect property ID") @Min(1L) private Long propertyId;
}
