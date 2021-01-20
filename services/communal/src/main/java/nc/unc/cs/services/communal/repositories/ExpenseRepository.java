package nc.unc.cs.services.communal.repositories;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.communal.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense findExpenseByExpenseId(Long expenseId);
    List<Expense> findExpensesByCitizenId(Long citizenId);
    List<Expense> findExpensesByCitizenIdAndIsPaid(Long citizenId, Boolean isPaid);
    List<Expense> findExpensesByCitizenIdAndDateBetween(Long citizenId, Date start, Date end);
}
