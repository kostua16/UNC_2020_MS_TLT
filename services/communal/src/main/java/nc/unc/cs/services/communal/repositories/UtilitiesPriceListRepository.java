package nc.unc.cs.services.communal.repositories;

import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitiesPriceListRepository extends JpaRepository<UtilitiesPriceList, Long> {

    UtilitiesPriceList findUtilitiesPriceListByUtilitiesPriceListId(Long expenseCostId);
    UtilitiesPriceList findUtilitiesPriceListByRegion(String region);
}