package nc.unc.cs.services.communal.controllers;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Utilities Price List API")
public class UtilitiesPriceListController {

    private final CommunalService communalService;

    public UtilitiesPriceListController(final CommunalService communalService) {
        this.communalService = communalService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(
        httpMethod = "POST",
        value = "Creating and adding price lists",
        notes = "Creation and addition of price lists for calculating the amount of spent utilities.",
        nickname = "addUtilitiesPriceList"
    )
    @ApiResponses({
        @ApiResponse(code = 400, message = "UtilitiesPriceList with ID = null", response = UtilitiesPriceList.class)
    })
    @ApiImplicitParam(
        name = "utilitiesPriceList",
        value = "Data for registration of the price list",
        required = true,
        type = "UtilitiesPriceList",
        dataType = "UtilitiesPriceList",
        dataTypeClass = UtilitiesPriceList.class,
        paramType = "body"
    )
    public ResponseEntity<UtilitiesPriceList> addUtilitiesPriceList(
        @RequestBody final UtilitiesPriceList utilitiesPriceList
    ) {
        return this.communalService.addUtilitiesPriceList(utilitiesPriceList);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(
        httpMethod = "GET",
        value = "List of UtilitiesPriceList",
        nickname = "getAllUtilitiesPriceList"
    )
    public List<UtilitiesPriceList> getAllUtilitiesPriceList() {
        return this.communalService.getAllUtilitiesPriceList();
    }
}
