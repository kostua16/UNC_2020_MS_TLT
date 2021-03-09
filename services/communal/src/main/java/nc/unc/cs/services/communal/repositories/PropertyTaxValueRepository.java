package nc.unc.cs.services.communal.repositories;

import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTaxValueRepository extends JpaRepository<PropertyTaxValue, Long> {

  PropertyTaxValue findPropertyTaxValueByPropertyTaxValueId(Long propertyTaxValueId);

  PropertyTaxValue findPropertyTaxValueByRegion(String region);
}
