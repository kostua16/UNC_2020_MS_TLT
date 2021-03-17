package nc.unc.cs.services.communal.repositories;

import nc.unc.cs.services.communal.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

  Property findPropertyByPropertyId(Long propertyId);

  List<Property> findPropertyByCitizenId(Long citizenId);

  Property findPropertyByRegionAndCityAndStreetAndHouseAndApartment(
      String region, String city, String street, String house, String apartment);
}
