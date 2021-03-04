package nc.unc.cs.services.communal.controllers.mock.price_lists.tax;

import nc.unc.cs.services.communal.controllers.PropertyTaxValueController;
import nc.unc.cs.services.communal.controllers.mock.ParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationPropertyTaxValue;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {PropertyTaxValueController.class})
public class PropertyTaxValueParent extends ParentWeb {
    protected static final String PROPERTY_TAX_VALUE_CONTROLLER_MAPPING = "http://localhost:8083/communal/tax/price";
    @MockBean
    protected PropertyTaxService propertyTaxService;

    protected final PropertyTaxValue createPropertyTaxValue() {
        final CreationPropertyTaxValue creationPropertyTaxValue =
            this.createCreationPropertyTaxValue();
        return PropertyTaxValue
            .builder()
            .propertyTaxValueId(1L)
            .region(creationPropertyTaxValue.getRegion())
            .pricePerSquareMeter(creationPropertyTaxValue.getPricePerSquareMeter())
            .cadastralValue(creationPropertyTaxValue.getCadastralValue())
            .build();
    }

    protected CreationPropertyTaxValue createCreationPropertyTaxValue() {
        return CreationPropertyTaxValue
            .builder()
            .region("samara ")
            .pricePerSquareMeter(100000)
            .cadastralValue(20)
            .build();
    }
}
