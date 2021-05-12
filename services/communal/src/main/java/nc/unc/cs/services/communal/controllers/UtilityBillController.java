package nc.unc.cs.services.communal.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import nc.unc.cs.services.communal.controllers.payloads.UtilitiesPayload;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.services.CommunalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/utilities")
@Tag(name = "Utility Bill Api", description = "API for creating utility bill")
public class UtilityBillController {

  private final CommunalService communalService;

  @Autowired
  public UtilityBillController(final CommunalService communalService) {
    this.communalService = communalService;
  }

  @PostMapping(value = "create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<UtilityBill> createUtilityBill(
      @Validated @RequestBody final UtilitiesPayload utilitiesPayload) {
    return this.communalService.calculateUtilityBill(utilitiesPayload);
  }

  @PutMapping(value = "/{paymentRequestId}")
  public ResponseEntity<UtilityBill> changeUtilityBillPaymentStatus(
      @Validated @PathVariable("paymentRequestId") final Long paymentRequestId) {
    return this.communalService.changeUtilityBillPaymentStatus(paymentRequestId);
  }

  @GetMapping(value = "/citizen/{citizenId}", produces = "application/json")
  public List<UtilityBill> getCitizenUtilityBills(@PathVariable("citizenId") final Long citizenId) {
    return this.communalService.getCitizenUtilityBills(citizenId);
  }

  @GetMapping(value = "all", produces = "application/json")
  public List<UtilityBill> getAllUtilityBills() {
    return this.communalService.getAllUtilityBills();
  }
}
