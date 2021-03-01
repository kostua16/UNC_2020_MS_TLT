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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @NotBlank(message = "Incorrect region name")
    @Size(min = 2, max = 40, message = "Incorrect region name")
    @Column(nullable = false, updatable = false, length = 40)
    private String region;

    @NotBlank(message = "Incorrect city name")
    @Size(min = 2, max = 40, message = "Incorrect city name")
    @Column(nullable = false, updatable = false, length = 40)
    private String city;

    @NotBlank(message = "Incorrect street name")
    @Size(min = 2, max = 40, message = "Incorrect street name")
    @NotBlank(message = "street not specified")
    @Column(nullable = false, updatable = false, length = 40)
    private String street;

    @NotBlank(message = "Incorrect house name")
    @Size(min = 2, max = 40, message = "Incorrect house name")
    @Column(nullable = false, updatable = false, length = 40)
    private String house;

    @NotBlank(message = "Incorrect apartment name")
    @Size(min = 2, max = 40, message = "Incorrect apartment name")
    @Column(nullable = false, updatable = false, length = 40)
    private String apartment;

    @NotNull(message = "Incorrect Apartment size")
    @Min(value = 10, message = "Apartment size is incorrect")
    @Column(nullable = false, updatable = false)
    private Integer apartmentSize;

    @NotNull(message = "Incorrect citizen ID")
    @Min(1L)
    @Column(nullable = false)
    private Long citizenId;

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
