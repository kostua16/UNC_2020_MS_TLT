package nc.unc.cs.services.communal.controllers.payloads;

import javax.validation.constraints.Max;
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
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CreationPropertyTaxValue {
  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  private String region;

  @NotNull(message = "Incorrect price per square meter size")
  @Min(value = 1, message = "Apartment price per square meter is incorrect")
  private Integer pricePerSquareMeter;

  @NotNull(message = "Incorrect cadastral value size")
  @Min(value = 1, message = "Apartment cadastral value is incorrect")
  @Max(value = 100, message = "Apartment cadastral value is incorrect")
  private Integer cadastralValue;

  @Builder
  public CreationPropertyTaxValue(final String region,
                                  final Integer pricePerSquareMeter,
                                  final Integer cadastralValue) {
    this.region = region.trim().toUpperCase();
    this.pricePerSquareMeter = pricePerSquareMeter;
    this.cadastralValue = cadastralValue;
  }

  public void setRegion(final String region) {
    this.region = region.trim().toUpperCase();
  }
}
