package nc.unc.cs.services.common.clients.passport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRegistrationIdDto {
  @NotNull
  @Min(value = 1L)
  private Long registrationId;
}
