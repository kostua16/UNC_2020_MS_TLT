package nc.unc.cs.services.common.clients.tax;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class IdInfo {
  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  private Long citizenId;

  @NotNull(message = "Incorrect service ID")
  @Min(1L)
  private Long serviceId;
}
