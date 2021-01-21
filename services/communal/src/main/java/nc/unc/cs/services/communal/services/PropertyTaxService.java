package nc.unc.cs.services.communal.services;

import nc.unc.cs.services.communal.integrations.BankService;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyTaxService {
    private static final Logger logger = LoggerFactory.getLogger(PropertyTaxService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyTaxRepository propertyTaxRepository;
    private final PropertyTaxValueRepository propertyTaxValueRepository;
    private final BankService bankService;

    @Autowired
    public PropertyTaxService(
        final PropertyRepository propertyRepository,
        final PropertyTaxRepository propertyTaxRepository,
        final PropertyTaxValueRepository propertyTaxValueRepository,
        final BankService bankService
    ) {
        this.propertyRepository = propertyRepository;
        this.propertyTaxRepository = propertyTaxRepository;
        this.propertyTaxValueRepository = propertyTaxValueRepository;
        this.bankService = bankService;
    }
}
