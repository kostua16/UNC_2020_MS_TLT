package nc.unc.cs.services.passport.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Domestic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long domesticId;

  @NotNull
  @Min(value = 1L)
  private Long registrationId;

  @NotBlank(message = "Incorrect name")
  @Size(min = 2, max = 40, message = "Incorrect name size")
  @Column(nullable = false, length = 40)
  private String name;

  @NotBlank(message = "Incorrect surname")
  @Size(min = 2, max = 40, message = "Incorrect surname size")
  @Column(nullable = false, length = 40)
  private String surname;

  @NotNull
  @Column(updatable = false, nullable = false)
  @Temporal(value = TemporalType.DATE)
  private Date dateOfBirth;

  @NotNull
  @Column(nullable = false)
  private Boolean isActive;

  @NotNull(message = "Incorrect tax amount")
  @Min(1111)
  @Column(updatable = false, nullable = false)
  private Integer series;

  @NotNull(message = "Incorrect tax amount")
  @Min(111111)
  @Column(updatable = false, nullable = false)
  private Integer number;

  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  @Column(updatable = false, nullable = false, unique = true)
  private Long citizenId;

  @Builder
  public Domestic(
      final Long domesticId,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final Boolean isActive,
      final Integer series,
      final Integer number,
      final Long citizenId) {
    this.domesticId = domesticId;
    this.registrationId = null;
    this.name = name.trim().toUpperCase();
    this.surname = surname.trim().toUpperCase();
    this.dateOfBirth = dateOfBirth;
    this.isActive = isActive;
    this.series = series;
    this.number = number;
    this.citizenId = citizenId;
  }

  public void setName(final String name) {
    this.name = name.trim().toUpperCase();
  }

  public void setSurname(final String surname) {
    this.surname = surname.trim().toUpperCase();
  }
}
