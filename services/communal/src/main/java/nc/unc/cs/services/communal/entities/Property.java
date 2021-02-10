package nc.unc.cs.services.communal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @NonNull
    @Column(nullable = false, updatable = false, length = 40)
    private String region;

    @NonNull
    @Column(nullable = false, updatable = false, length = 40)
    private String city;

    @NonNull
    @Column(nullable = false, updatable = false, length = 40)
    private String street;

    @NonNull
    @JsonFormat()
    @Column(nullable = false, updatable = false, length = 40)
    private String house;

    @NonNull
    @Column(nullable = false, updatable = false, length = 40)
    private String apartment;

    @NonNull
    @Column(nullable = false, updatable = false)
    private Integer apartmentSize;

    @NonNull
    @Column(nullable = false)
    private Long citizenId;

    public void setRegion(String region) {
        this.region = region.trim().toUpperCase();
    }

    public void setCity(String city) {
        this.city = city.trim().toUpperCase();
    }

    public void setStreet(String street) {
        this.street = street.trim().toUpperCase();
    }

    public void setHouse(String house) {
        this.house = house.trim().toUpperCase();
    }

    public void setApartment(String apartment) {
        this.apartment = apartment.trim().toUpperCase();
    }
}
