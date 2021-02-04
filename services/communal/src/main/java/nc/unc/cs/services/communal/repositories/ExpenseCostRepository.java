package nc.unc.cs.services.communal.repositories;

import nc.unc.cs.services.communal.entities.ExpenseCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCostRepository extends JpaRepository<ExpenseCost, Long> {

    ExpenseCost findExpenseCostByExpenseCostId(Long expenseCostId);
    ExpenseCost findExpenseCostByRegion(String region);
}