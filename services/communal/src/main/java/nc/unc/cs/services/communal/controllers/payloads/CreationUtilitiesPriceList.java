package nc.unc.cs.services.communal.controllers.payloads;

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
public class CreationUtilitiesPriceList {
  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  private String region;

  @NotNull(message = "Incorrect cold water price size")
  @Min(value = 1, message = "Apartment cold water price is incorrect")
  private Integer coldWaterPrice;

  @NotNull(message = "Incorrect hot water pricer size")
  @Min(value = 1, message = "Apartment hot water price is incorrect")
  private Integer hotWaterPrice;

  @NotNull(message = "Incorrect electricity price size")
  @Min(value = 1, message = "Apartment cold water price is incorrect")
  private Integer electricityPrice;

  @Builder
  public CreationUtilitiesPriceList(
      final String region,
      final Integer coldWaterPrice,
      final Integer hotWaterPrice,
      final Integer electricityPrice) {
    this.region = region.trim().toUpperCase();
    this.coldWaterPrice = coldWaterPrice;
    this.hotWaterPrice = hotWaterPrice;
    this.electricityPrice = electricityPrice;
  }

  public void setRegion(final String region) {
    this.region = region.trim().toUpperCase();
  }
}
