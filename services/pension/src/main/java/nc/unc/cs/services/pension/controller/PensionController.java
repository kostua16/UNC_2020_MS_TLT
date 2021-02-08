package nc.unc.cs.services.pension.controller;

import nc.unc.cs.services.pension.model.Pension;
import nc.unc.cs.services.pension.service.PensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pension")
public class PensionController {
    private final PensionService pensionService;

    @Autowired
    public PensionController(final PensionService pensionService) {this.pensionService = pensionService;}

    @PostMapping(value = "/createPension", produces = "application/json")
    public ResponseEntity<Pension> createPension(@RequestBody final Pension pension) {
        return this.pensionService.createPension(pension);
    }
}
