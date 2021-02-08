package nc.unc.cs.services.communal.entities;

import nc.unc.cs.services.communal.SpringCommunalTest;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

//@DataJpaTest
@AutoConfigureTestEntityManager
public class PropertyTest extends SpringCommunalTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void testRepo() {
        Property property = new Property();
        property.setRegion("    samara   ");
        property.setCity("samara");
        property.setStreet("main");
        property.setHouse("12b");
        property.setApartment("123");
        property.setApartmentSize(100);
        property.setCitizenId(111L);

        this.propertyRepository.save(property);

        Assertions.assertEquals("SAMARA", property.getRegion());
    }
}
