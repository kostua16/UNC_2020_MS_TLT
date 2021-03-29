package nc.unc.cs.services.passport.model;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Citizen {
  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String name;

  @NotBlank(message = "Incorrect region surname")
  @Size(min = 2, max = 40, message = "Incorrect region surname")
  @Column(nullable = false, length = 40)
  private String surname;

  @NotNull
  @Column(updatable = false, nullable = false)
  private Date dateOfBirth;

  @NotBlank(message = "Incorrect region registration")
  @Size(min = 2, max = 40, message = "Incorrect region registration")
  @Column(nullable = false, length = 40)
  private String registration;

  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  @Column(updatable = false, nullable = false)
  private Long citizenId;

  @Builder
  public Citizen(
      final String name,
      final String surname,
      final Date dateOfBirth,
      final String registration,
      final Long citizenId
  ) {
    this.name = name.trim().toUpperCase();
    this.surname = surname.trim().toUpperCase();
    this.dateOfBirth = dateOfBirth;
    this.registration = registration.trim().toUpperCase();
    this.citizenId = citizenId;
  }

  public void setRegistration(String registration) {
    this.registration = registration.trim().toUpperCase();
  }

  public void setName(String name) {
    this.name = name.trim().toUpperCase();
  }

  public void setSurname(String surname) {
    this.surname = surname.trim().toUpperCase();
  }
}
