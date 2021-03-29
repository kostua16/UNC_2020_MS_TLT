package nc.unc.cs.services.passport.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


public class InternationalDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long internationalId;

  @NotBlank(message = "Incorrect region name")
  @Column(nullable = false)
  private Boolean locked;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String name;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String surname;

  @NotBlank(message = "Incorrect house name")
  private Date dateOfBirth;

  @NotBlank(message = "Incorrect house name")
  @Column(nullable = false)
  private Boolean isActive;

  @NotBlank(message = "Incorrect house name")
  @Min(1L)
  @Column(nullable = false)
  private Long citizenId;

  public InternationalDTO(
      final Long internationalId,
      final Boolean locked,
      final String name,
      final String surname,
      final Date dateOfBirth,
      final Boolean isActive,
      final Long citizenId
  ) {
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
