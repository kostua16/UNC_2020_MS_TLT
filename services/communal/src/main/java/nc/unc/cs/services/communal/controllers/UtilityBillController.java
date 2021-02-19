package nc.unc.cs.services.communal.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.License;
import nc.unc.cs.services.communal.controllers.payloads.UtilitiesPayload;
import nc.unc.cs.services.communal.entities.UtilityBill;
import nc.unc.cs.services.communal.services.CommunalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/utilities/")
@CrossOrigin
@Api(value = "API for creating utility bill")
public class UtilityBillController {
    private final CommunalService communalService;

    @Autowired
    public UtilityBillController(final CommunalService communalService) {
        this.communalService = communalService;
    }

    @PostMapping(value = "create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UtilityBill> createUtilityBill(@RequestBody final UtilitiesPayload utilitiesPayload) {
        return this.communalService.calculateUtilityBill(utilitiesPayload);
    }

    @GetMapping(value = "all", produces = "application/json")
    public List<UtilityBill> getAllUtilityBills() {
        return this.communalService.getAllUtilityBills();
    }
}
