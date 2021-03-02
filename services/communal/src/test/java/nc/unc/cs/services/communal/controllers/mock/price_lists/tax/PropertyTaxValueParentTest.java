package nc.unc.cs.services.communal.controllers.mock.price_lists.tax;

import nc.unc.cs.services.communal.controllers.PropertyTaxValueController;
import nc.unc.cs.services.communal.controllers.mock.ParentWebTest;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.services.PropertyTaxService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {PropertyTaxValueController.class})
public class PropertyTaxValueParentTest extends ParentWebTest {
    protected static final String REGISTRATION_CONTROLLER_MAPPING = "http://localhost:8083/communal/tax/price";
    @MockBean
    protected PropertyTaxService propertyTaxService;

    public final PropertyTaxValue createPropertyTaxValue() {
        return PropertyTaxValue
            .builder()
            .propertyTaxValueId(1L)
            .region("samara ")
            .pricePerSquareMeter(10000)
            .cadastralValue(20)
            .build();
    }
}
