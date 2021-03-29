package nc.unc.cs.services.passport.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Domestic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long domesticId;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String registration;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String name;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String surname;

  @NotNull
  @Column(updatable = false, nullable = false)
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
  @Column(updatable = false, nullable = false)
  private Long citizenId;

  @Builder
  public Domestic(
      final Long domesticId,
      final String registration,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final Boolean isActive,
      final Integer series,
      final Integer number,
      final Long citizenId
  ) {
    this.domesticId = domesticId;
    this.registration = registration.trim().toUpperCase();
    this.name = name.trim().toUpperCase();
    this.surname = surname.trim().toUpperCase();
    this.dateOfBirth = dateOfBirth;
    this.isActive = isActive;
    this.series = series;
    this.number = number;
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

