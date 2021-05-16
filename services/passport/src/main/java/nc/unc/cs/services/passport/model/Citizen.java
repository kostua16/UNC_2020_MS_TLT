package nc.unc.cs.services.passport.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Citizen {
  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  private String name;

  @NotBlank(message = "Incorrect region surname")
  @Size(min = 2, max = 40, message = "Incorrect region surname")
  private String surname;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Temporal(value = TemporalType.DATE)
  private Date dateOfBirth;

  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  private Long citizenId;

  @Builder
  public Citizen(
      final String name, final String surname, final Date dateOfBirth, final Long citizenId) {
    this.name = name.trim().toUpperCase();
    this.surname = surname.trim().toUpperCase();
    this.dateOfBirth = dateOfBirth;
    this.citizenId = citizenId;
  }

  public void setName(final String name) {
    this.name = name.trim().toUpperCase();
  }

  public void setSurname(final String surname) {
    this.surname = surname.trim().toUpperCase();
  }
}
