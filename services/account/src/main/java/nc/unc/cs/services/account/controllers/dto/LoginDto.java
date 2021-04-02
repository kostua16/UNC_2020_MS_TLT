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
public class LoginDto {
  @NotBlank
  @Size(min = 5, max = 20, message = "Incorrect username size!")
  private String username;

  @NotBlank
  @Size(min = 5, max = 20, message = "Incorrect password size!")
  private String password;

  @Builder
  public LoginDto(
      final String username,
      final String password
  ) {
    this.username = username.trim();
    this.password = password.trim();
  }

  public void setUsername(final String username) {
    this.username = username.trim();
  }

  public void setPassword(final String password) {
    this.password = password.trim();
  }
}
