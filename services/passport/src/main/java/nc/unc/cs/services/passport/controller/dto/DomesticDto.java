package nc.unc.cs.services.passport.controller.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DomesticDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long domesticId;

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

  public DomesticDto(
      final Long domesticId,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final Boolean isActive,
      final Integer series,
      final Integer number,
      final Long citizenId) {
    this.domesticId = domesticId;
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
