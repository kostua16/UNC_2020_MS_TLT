package nc.unc.cs.services.gibdd.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

  @NotNull(message = "Car's number can't be null")
  @NotEmpty(message = "Car's number can't be empty")
  @Length(min = 4, max = 7, message = "Car's number should have [4-7] length")
  private String number;

  @NotNull(message = "Car's owner can't be null")
  @NotEmpty(message = "Car's owner can't be empty")
  private String owner;
}
