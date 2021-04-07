package nc.unc.cs.services.account.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
  @Size(min = 2, max = 40)
  private String name;

  @NotBlank
  @Size(min = 2, max = 40)
  private String surname;

  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Temporal(value = TemporalType.DATE)
  @NotNull
  private Date dateOfBirth;

  @NotBlank
  @Size(min = 2, max = 40)
  private String registration;

  @Builder
  public RegistrationDto(
      final String username,
      final String password,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final String registration) {
    this.username = username.trim();
    this.password = password.trim();
    this.name = name.trim();
    this.surname = surname.trim();
    this.dateOfBirth = dateOfBirth;
    this.registration = registration.trim();
  }
}
