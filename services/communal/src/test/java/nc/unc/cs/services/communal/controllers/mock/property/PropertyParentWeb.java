package nc.unc.cs.services.communal.controllers.mock.property;

import nc.unc.cs.services.communal.controllers.PropertyController;
import nc.unc.cs.services.communal.controllers.mock.ParentWeb;
import nc.unc.cs.services.communal.controllers.payloads.CreationProperty;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.services.RegistrationService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {PropertyController.class})
public class PropertyParentWeb extends ParentWeb {
    protected static final String PROPERTY_CONTROLLER_MAPPING = "http://localhost:8083/communal/property";
    @MockBean
    protected RegistrationService registrationService;

    protected final CreationProperty createCreationProperty() {
        return CreationProperty
            .builder()
            .region("Samara")
            .city("Samara")
            .street("main")
            .house("111")
            .apartment("1b")
            .apartmentSize(1000)
            .citizenId(111L)
            .build();
    }

    protected final Property createProperty() {
        final CreationProperty property = this.createCreationProperty();
        return Property.builder()
            .region(property.getRegion())
            .city(property.getCity())
            .street(property.getStreet())
            .house(property.getHouse())
            .apartment(property.getApartment())
            .apartmentSize(property.getApartmentSize())
            .citizenId(property.getCitizenId())
            .build();
    }
}
