package nc.unc.cs.services.pension.controller;

import java.util.Date;
import nc.unc.cs.services.common.account.Citizen;
import nc.unc.cs.services.pension.model.Pension;
import nc.unc.cs.services.pension.service.PensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("pension")
public class PensionController {
  private final PensionService pensionService;

  @Autowired
  public PensionController(final PensionService pensionService) {
    this.pensionService = pensionService;
  }

  @PostMapping(value = "/createPension", produces = "application/json")
  public ResponseEntity<Pension>
  createPension(@RequestBody final Citizen citizen) {
    try {
      return this.pensionService.createPension(citizen);
    } catch (Exception ex) { // тут надо ловить свои ошибки или делать через
                             // controlleradvice
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Failed to create pension!");
    }
  }
}
