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
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CreationProperty {
  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  private String region;

  @NotBlank(message = "Incorrect city name")
  @Size(min = 2, max = 40, message = "Incorrect city name")
  private String city;

  @NotBlank(message = "Incorrect street name")
  @Size(min = 2, max = 40, message = "Incorrect street name")
  private String street;

  @NotBlank(message = "Incorrect house name")
  @Size(min = 2, max = 40, message = "Incorrect house name")
  private String house;

  @NotBlank(message = "Incorrect apartment name")
  @Size(min = 2, max = 40, message = "Incorrect apartment name")
  private String apartment;

  @NotNull(message = "Incorrect Apartment size")
  @Min(value = 10, message = "Apartment size is incorrect")
  private Integer apartmentSize;

  @NotNull(message = "Incorrect citizen ID") @Min(1L) private Long citizenId;

  @Builder
  public CreationProperty(final String region, final String city,
                          final String street, final String house,
                          final String apartment, final Integer apartmentSize,
                          final Long citizenId) {
    this.region = region.trim().toUpperCase();
    this.city = city.trim().toUpperCase();
    this.street = street.trim().toUpperCase();
    this.house = house.trim().toUpperCase();
    this.apartment = apartment.trim().toUpperCase();
    this.apartmentSize = apartmentSize;
    this.citizenId = citizenId;
  }

  public void setRegion(final String region) {
    this.region = region.trim().toUpperCase();
  }

  public void setCity(final String city) {
    this.city = city.trim().toUpperCase();
  }

  public void setStreet(final String street) {
    this.street = street.trim().toUpperCase();
  }

  public void setHouse(final String house) {
    this.house = house.trim().toUpperCase();
  }

  public void setApartment(final String apartment) {
    this.apartment = apartment.trim().toUpperCase();
  }
}
