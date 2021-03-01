package nc.unc.cs.services.tax.services;

import java.util.Date;
import java.util.List;
import nc.unc.cs.services.common.clients.tax.TaxPayment;
import nc.unc.cs.services.tax.entities.Tax;
import nc.unc.cs.services.tax.exceptions.TaxNotFoundException;
import nc.unc.cs.services.tax.repositories.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Tax service.
 * @since 0.1.9
 */
@Service
public class TaxService {
    private static final Logger logger = LoggerFactory.getLogger(TaxService.class);

    private final TaxRepository taxRepository;

    @Autowired
    public TaxService(
        final TaxRepository taxRepository
    ) {
        this.taxRepository = taxRepository;
    }

    /**
     * Checking tax status.
     *
     * @param taxId The ID of the tax by which the tax is searched in the database;
     * @return Tax status;
     */
    public Boolean isPaid(final Long taxId) {
        return this.taxRepository
            .findById(taxId)
            .orElseThrow(() -> new TaxNotFoundException(taxId))
            .getStatus();
    }

    /**
     * Service tax creation.
     *
     * @param serviceId The ID of the service that provided the service;
     * @param citizenId The Id of the citizen (account);
     * @param taxAmount Service tax;
     *
     * @return tax id
     */
    public Long createTax(
        final Long serviceId,
        final Long citizenId,
        final Integer taxAmount
    ) {
        final Tax tax = Tax
            .builder()
            .taxAmount(taxAmount)
            .status(false)
            .creationDate(new Date())
            .taxPaymentDate(null)
            .citizenId(citizenId)
            .serviceId(serviceId)
            .build();
        this.taxRepository.save(tax);
        return tax.getTaxId();
    }

    /**
     * Payment of tax.
     *
     * @param taxPayment object with tax id and payment date
     * @return ResponseEntity with tax id;
     */
    public ResponseEntity<Long> payTax(final TaxPayment taxPayment) {
        Tax changeTax = this.taxRepository
            .findById(taxPayment.getTaxId())
            .orElseThrow(() -> new TaxNotFoundException(taxPayment.getTaxId()));

        if (changeTax.getStatus()) {
            logger.error("Tax with ID = {} already paid!", changeTax.getTaxId());
            return ResponseEntity.status(400).body(changeTax.getTaxId());
        }

        changeTax.setStatus(true);
        changeTax.setTaxPaymentDate(taxPayment.getTaxPaymentDate());

        logger.info("Tax with ID = {} has been payed", taxPayment.getTaxId());

        return ResponseEntity.ok(this.taxRepository.save(changeTax).getTaxId());
    }

    /**
     * Debt check
     *
     * @param serviceId The ID of the service that provided the service;
     * @param citizenId The Id of the citizen (account);
     * @return true - if there are no debts, otherwise false
     */
    public Boolean isNotDebtor(final Long serviceId, final Long citizenId) {
        return this.taxRepository
            .findTaxesByServiceIdAndCitizenIdAndStatus(serviceId, citizenId, false)
            .isEmpty();
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