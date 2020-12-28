package nc.unc.cs.services.passport.controller;


import nc.unc.cs.services.passport.model.Citizen;
import nc.unc.cs.services.passport.model.Domestic;
import nc.unc.cs.services.passport.model.International;
import nc.unc.cs.services.passport.service.PassportTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("passport")
public class PassportController {
    private final PassportTable passportTable;

    @Autowired
    public PassportController(final PassportTable passportTable) {
        this.passportTable = passportTable;
    }

    @GetMapping(value = "/allInternational" , produces = "application/json")
    public Iterable <International>  AllInternational() {
        return this.passportTable.getInternational();
    }

    @GetMapping(value = "/international/{id}" , produces = "application/json")
    public International  getInternationalById(@PathVariable Long id) {
        return this.passportTable.getInternationalById(id);
    }

    @GetMapping(value = "/allDomestic" , produces = "application/json")
    public Iterable <Domestic>  AllDomestic() {
        return this.passportTable.getDomestic();
    }

    @GetMapping(value = "/domestic/{id}", produces = "application/json")
    public Domestic getDomesticById(@PathVariable Long id) {
        return this.passportTable.getDomesticById(id);
    }

    @PostMapping(value = "/registerDomestic", produces = "application/json")
    public Domestic registerDomesticPassport(@RequestBody final Citizen citizen) {
        return this.passportTable.registerDomesticPassport(citizen);
    }

    @PostMapping(value = "/registerInternational", produces = "application/json")
    public International registerInternationalPassport(@RequestBody final Citizen citizen) {
        return this.passportTable.registerInternationalPassport(citizen);
    }

    @PostMapping(value = "/updateDomestic/{id}", produces = "application/json")
    public Domestic updateDomesticPassport(@PathVariable Long id, @RequestBody Domestic domestic) throws Exception {
        return this.passportTable.updateDomestic(id,domestic);
    }

    @PostMapping(value = "/activateDomestic/{id}", produces = "application/json")
    public Domestic activateDomesticPassport(@PathVariable Long id) throws Exception {
        return this.passportTable.activateDomestic(id);
    }
}
