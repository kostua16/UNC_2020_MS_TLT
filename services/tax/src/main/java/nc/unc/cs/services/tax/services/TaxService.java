package nc.unc.cs.services.tax.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.exceptions.TaxNotFoundException;
import nc.unc.cs.services.tax.repositories.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Tax service
 * @since 0.1.1
 */
@Service
public class TaxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxService.class);

    private final TaxRepository taxRepository;

    @Autowired
    public TaxService(
        final TaxRepository taxRepository
    ) {
        this.taxRepository = taxRepository;
    }

    public Boolean isPaid(final Long taxId) {

        return this.taxRepository
            .findById(taxId)
            .orElseThrow(() -> new TaxNotFoundException(taxId)) // return ???
            .getStatus();
    }

    public Long createTax(
        final Long serviceId,
        final Long citizenId,
        Integer taxAmount
    ) {
        return this.taxRepository.save(new Tax(taxAmount, citizenId, serviceId)).getTaxId();
    }

    public void payTax( // void to ResponseEntity
                        final Long taxId,
                        final Date taxPaymentDate
    ) {
        Tax changeTax = this.taxRepository
            .findById(taxId)
            .orElseThrow(() -> new TaxNotFoundException(taxId));

        changeTax.setStatus(true);
        changeTax.setTaxPaymentDate(taxPaymentDate);

        this.taxRepository.save(changeTax);
    }

    public List<Tax> getTaxes(final Long citizenId) {
        LOGGER.info("Providing all taxes for citizen with ID: {}", citizenId);
        return this.taxRepository.findTaxesByCitizenId(citizenId);
    }

    public List<Tax> getListUnpaidTaxes(final Long serviceId, final Long citizenId) {
        // false - неоплачченные (заглушка)
        return this.taxRepository
            .findTaxesByServiceIdAndCitizenIdAndStatus(serviceId, citizenId, false);
    }

    public List<Tax> getPage(
        final Integer pageNumber,
        final Integer size,
        final Boolean status,
        final String column
    ) {
        Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, size, Sort.by(column));

        return this.taxRepository
            .findAllByStatus(status, firstPageWithTwoElements)
            .getContent();

    }
}