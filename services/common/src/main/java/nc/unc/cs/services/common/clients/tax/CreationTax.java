package nc.unc.cs.services.common.clients.tax;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CreationTax {

  @NotNull(message = "Incorrect service ID")
  @Min(1L)
  private Long serviceId;

  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  private Long citizenId;

  @NotNull(message = "Incorrect tax amount")
  @Min(1)
  private Integer taxAmount;
}
