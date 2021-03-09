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
public class Registration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long registrationId;

  @NotBlank(message = "Incorrect region name")
  @Size(min = 2, max = 40, message = "Incorrect region name")
  @Column(nullable = false, length = 40)
  private String region;

  @NotBlank(message = "Incorrect city name")
  @Size(min = 2, max = 40, message = "Incorrect city name")
  @Column(nullable = false, length = 40)
  private String city;

  @NotBlank(message = "Incorrect street name")
  @Size(min = 2, max = 40, message = "Incorrect street name")
  @Column(nullable = false, length = 40)
  private String street;

  @NotBlank(message = "Incorrect house name")
  @Size(min = 2, max = 40, message = "Incorrect house name")
  @Column(nullable = false, length = 10)
  private String house;

  @NotBlank(message = "Incorrect apartment name")
  @Size(min = 2, max = 40, message = "Incorrect apartment name")
  @Column(nullable = false, length = 10)
  private String apartment;

  @NotNull
  @Column(nullable = false)
  private Boolean isActive;

  @NotNull(message = "Incorrect citizen ID")
  @Min(1L)
  @Column(nullable = false)
  private Long citizenId;

  @Builder
  public Registration(
      final Long registrationId,
      final String region,
      final String city,
      final String street,
      final String house,
      final String apartment,
      final Boolean isActive,
      final Long citizenId) {
    this.registrationId = registrationId;
    this.region = region.trim().toUpperCase();
    this.city = city.trim().toUpperCase();
    this.street = street.trim().toUpperCase();
    this.house = house.trim().toUpperCase();
    this.apartment = apartment.trim().toUpperCase();
    this.isActive = isActive;
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
