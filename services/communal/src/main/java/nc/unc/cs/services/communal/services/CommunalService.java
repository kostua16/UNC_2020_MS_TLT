package nc.unc.cs.services.communal.services;

import nc.unc.cs.services.communal.repositories.ExpenseCostRepository;
import nc.unc.cs.services.communal.repositories.ExpenseRepository;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunalService {

    private final PropertyRepository propertyRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseCostRepository expenseCostRepository;

    @Autowired
    public CommunalService(
        final PropertyRepository propertyRepository,
        final ExpenseRepository expenseRepository,
        final ExpenseCostRepository expenseCostRepository
    ) {
        this.propertyRepository = propertyRepository;
        this.expenseRepository = expenseRepository;
        this.expenseCostRepository = expenseCostRepository;
    }
}
