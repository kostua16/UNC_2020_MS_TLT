package nc.unc.cs.services.communal.repositories;

import java.util.List;
import nc.unc.cs.services.communal.entities.PropertyTax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTaxRepository extends JpaRepository<PropertyTax, Long> {

  PropertyTax findPropertyTaxByPropertyTaxId(Long propertyTaxId);

  List<PropertyTax> findPropertyTaxByCitizenId(Long citizenId);

  List<PropertyTax> findPropertyTaxByCitizenIdAndIsPaid(Long citizenId, Boolean isPaid);

  List<PropertyTax> findPropertyTaxesByPropertyIdAndIsPaid(Long propertyId, Boolean isPaid);

  Page<PropertyTax> findAllByIsPaid(Boolean IsPaid, Pageable pageable);
}
