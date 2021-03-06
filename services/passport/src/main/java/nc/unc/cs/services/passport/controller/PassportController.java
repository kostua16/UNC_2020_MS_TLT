package nc.unc.cs.services.passport.controller;

import java.util.List;
import nc.unc.cs.services.common.clients.passport.UpdateRegistrationIdDto;
import nc.unc.cs.services.passport.controller.dto.DomesticDto;
import nc.unc.cs.services.passport.controller.dto.InternationalDto;
import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.model.International;
import nc.unc.cs.services.passport.service.PassportTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {
  private final PassportTable passportTable;

  @Autowired
  public PassportController(final PassportTable passportTable) {
    this.passportTable = passportTable;
  }

  @GetMapping(value = "/allInternational", produces = "application/json")
  public Iterable<International> AllInternational() {
    return this.passportTable.getInternational();
  }

  @GetMapping(value = "/international/{id}", produces = "application/json")
  public International getInternationalById(@PathVariable Long id) {
    return this.passportTable.getInternationalById(id);
  }

  @GetMapping(value = "/allDomestic", produces = "application/json")
  public Iterable<Domestic> AllDomestic() {
    return this.passportTable.getDomestic();
  }

  @GetMapping(value = "/domestic/{id}", produces = "application/json")
  public Domestic getDomesticById(@PathVariable Long id) {
    return this.passportTable.getDomesticById(id);
  }

  @PostMapping(value = "/registerDomestic", produces = "application/json")
  public ResponseEntity<Domestic> registerDomesticPassport(@RequestBody final Citizen citizen) {
    return this.passportTable.registerDomesticPassport(citizen);
  }

  @PostMapping(value = "/registerInternational", produces = "application/json")
  public ResponseEntity<International> registerInternationalPassport(
      @RequestBody final Citizen citizen) {
    return this.passportTable.registerInternationalPassport(citizen);
  }

  @PostMapping(value = "/updateDomestic/{id}", produces = "application/json")
  public ResponseEntity<Domestic> updateDomesticPassport(
      @PathVariable("id") Long id, @RequestBody DomesticDto domestic) {
    return this.passportTable.updateDomestic(id, domestic);
  }

  @PostMapping(value = "/activateDomestic/{id}", produces = "application/json")
  public Domestic activateDomesticPassport(@PathVariable Long id) throws Exception {
    return this.passportTable.activateDomestic(id);
  }

  @PostMapping(value = "/updateInternational/{id}", produces = "application/json")
  public ResponseEntity<International> updateInternationalPassport(
      @PathVariable("id") Long id, @RequestBody InternationalDto international) {
    return this.passportTable.updateInternational(id, international);
  }

  @PostMapping(value = "/activateInternational/{id}", produces = "application/json")
  public International activateInternationalPassport(@PathVariable Long id) throws Exception {
    return this.passportTable.activateInternational(id);
  }

  @GetMapping(value = "/domestic/citizen/{citizenId}", produces = "application/json")
  public Domestic getDomesticByCitizenId(@PathVariable("citizenId") final Long citizenId) {
    return this.passportTable.getDomesticByCitizenId(citizenId);
  }

  @GetMapping(value = "/international/citizen/{citizenId}", produces = "application/json")
  public List<International> getInternationalsByCitizenId(
      @PathVariable("citizenId") final Long citizenId) {
    return this.passportTable.getInternationalsByCitizenId(citizenId);
  }

  @PutMapping(value = "domestic/registration/{citizenId}", produces = "application/json")
  public ResponseEntity<Long> updateDomesticRegistration(
      @PathVariable("citizenId") final Long citizenId,
      @Validated @RequestBody final UpdateRegistrationIdDto updateRegistrationIdDto) {
    return this.passportTable.updateDomesticRegistration(
        citizenId, updateRegistrationIdDto.getRegistrationId());
  }
}
