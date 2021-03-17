package nc.unc.cs.services.communal.entities;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UtilitiesPriceList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long utilitiesPriceListId;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, unique = true, updatable = false, length = 40)
  private String region;

  @NotNull(message = "Incorrect cold water price size")
  @Min(value = 1, message = "Apartment cold water price is incorrect")
  @Column(nullable = false)
  private Integer coldWaterPrice;

  @NotNull(message = "Incorrect hot water pricer size")
  @Min(value = 1, message = "Apartment hot water price is incorrect")
  @Column(nullable = false)
  private Integer hotWaterPrice;

  @NotNull(message = "Incorrect electricity price size")
  @Min(value = 1, message = "Apartment cold water price is incorrect")
  @Column(nullable = false)
  private Integer electricityPrice;

  @Builder
  public UtilitiesPriceList(
      final Long utilitiesPriceListId,
      final String region,
      final Integer coldWaterPrice,
      final Integer hotWaterPrice,
      final Integer electricityPrice
  ) {
    this.utilitiesPriceListId = utilitiesPriceListId;
    this.region = region.trim().toUpperCase();
    this.coldWaterPrice = coldWaterPrice;
    this.hotWaterPrice = hotWaterPrice;
    this.electricityPrice = electricityPrice;
  }

  public void setRegion(final String region) {
    this.region = region.trim().toUpperCase();
  }
}
