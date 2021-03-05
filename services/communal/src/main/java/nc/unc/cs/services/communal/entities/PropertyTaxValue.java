package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@Entity
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PropertyTaxValue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long propertyTaxValueId;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, unique = true, updatable = false, length = 40)
  private String region;

  @NotNull(message = "Incorrect price per square meter size")
  @Min(value = 1, message = "Apartment price per square meter is incorrect")
  @Column(nullable = false)
  private Integer pricePerSquareMeter;

  @NotNull(message = "Incorrect cadastral value size")
  @Min(value = 1, message = "Apartment cadastral value is incorrect")
  @Max(value = 100, message = "Apartment cadastral value is incorrect")
  @Column(nullable = false)
  private Integer cadastralValue;

  @Builder
  public PropertyTaxValue(
      final Long propertyTaxValueId,
      final String region,
      final Integer pricePerSquareMeter,
      final Integer cadastralValue) {
    this.propertyTaxValueId = propertyTaxValueId;
    this.region = region.trim().toUpperCase();
    this.pricePerSquareMeter = pricePerSquareMeter;
    this.cadastralValue = cadastralValue;
  }

  public void setRegion(final String region) {
    this.region = region.trim().toUpperCase();
  }
}
