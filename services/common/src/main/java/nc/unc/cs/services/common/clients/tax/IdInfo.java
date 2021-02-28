package nc.unc.cs.services.common.clients.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class IdInfo {
  private Long citizenId;
  private Long serviceId;
}
