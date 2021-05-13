package nc.unc.cs.services.passport.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class International {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long internationalId;

  @NotNull
  @Column(nullable = false)
  private Boolean locked;

  @NotBlank(message = "Blank name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String name;

  @NotBlank(message = "Blank surname")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String surname;

  @NotNull
  @Column(nullable = false, updatable = false)
  private Date dateOfBirth;

  @NotNull
  @Column(nullable = false)
  private Boolean isActive;

  @NotNull
  @Min(1L)
  @Column(nullable = false, updatable = false)
  private Long citizenId;

  @Builder
  public International(
      final Long internationalId,
      final Boolean locked,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final Boolean isActive,
      final Long citizenId) {
    this.internationalId = internationalId;
    this.locked = locked;
    this.name = name.trim().toUpperCase();
    this.surname = surname.trim().toUpperCase();
    this.dateOfBirth = dateOfBirth;
    this.isActive = isActive;
    this.citizenId = citizenId;
  }

  public void setName(final String name) {
    this.name = name.trim().toUpperCase();
  }

  public void setSurname(final String surname) {
    this.surname = surname.trim().toUpperCase();
  }
}
