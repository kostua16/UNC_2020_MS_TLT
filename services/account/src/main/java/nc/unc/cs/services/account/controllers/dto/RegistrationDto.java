package nc.unc.cs.services.account.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RegistrationDto {

  @NotBlank
  @Size(min = 5, max = 20, message = "Incorrect username size!")
  private String username;

  @NotBlank
  @Size(min = 5, max = 20, message = "Incorrect password size!")
  private String password;

  @NotBlank
  @Size(min = 8, max = 40)
  private String email;

  @Builder
  public RegistrationDto(final String username, final String password, final String email) {
    this.username = username.trim();
    this.password = password.trim();
    this.email = email.trim();
  }
}
