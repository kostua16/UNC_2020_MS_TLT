package nc.unc.cs.services.communal.controllers;

import java.util.List;
import nc.unc.cs.services.communal.entities.UtilitiesPriceList;
import nc.unc.cs.services.communal.services.CommunalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "communal/utilities/price")
@CrossOrigin
public class UtilitiesPriceListController {

    private final CommunalService communalService;

    public UtilitiesPriceListController(final CommunalService communalService) {
        this.communalService = communalService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UtilitiesPriceList> addUtilitiesPriceList(
        @RequestBody final UtilitiesPriceList utilitiesPriceList
    ) {
        return this.communalService.addUtilitiesPriceList(utilitiesPriceList);
    }

    @GetMapping(produces = "application/json")
    public List<UtilitiesPriceList> getAllUtilitiesPriceList() {
        return this.communalService.getAllUtilitiesPriceList();
    }
}
