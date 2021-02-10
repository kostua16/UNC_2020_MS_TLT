package nc.unc.cs.services.communal.entities;

import nc.unc.cs.services.communal.SpringCommunalTest;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

//@DataJpaTest
@AutoConfigureTestEntityManager
public class PropertyTest extends SpringCommunalTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertyTest.class);
    private static final Property property = new Property();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void beforeEach() {
        property.setRegion("    samara   ");
        property.setCity("   samara   ");
        property.setStreet("   main   ");
        property.setHouse("   12b   ");
        property.setApartment("   123");
        property.setApartmentSize(100);
        property.setCitizenId(111L);

        this.propertyRepository.save(property);
    }

    private static String stringTrimUpper(final String str) {
        return str.trim().toUpperCase();
    }

    @Test
    public void testRepo() {

        final Property propertyFromDb = this.propertyRepository.findAll().get(0);

        logger.debug("Property from DB: {}", propertyFromDb);

        Assertions.assertAll(
            () -> Assertions.assertEquals(stringTrimUpper(property.getRegion()), propertyFromDb.getRegion()),
            () -> Assertions.assertEquals(stringTrimUpper(property.getCity()), propertyFromDb.getCity()),
            () -> Assertions.assertEquals(stringTrimUpper(property.getStreet()), propertyFromDb.getStreet()),
            () -> Assertions.assertEquals(stringTrimUpper(property.getHouse()), propertyFromDb.getHouse()),
            () -> Assertions.assertEquals(stringTrimUpper(property.getApartment()), propertyFromDb.getApartment()),
            () -> Assertions.assertNotNull(property.getPropertyId())
        );
    }

    @Test
    public void nullData() {
        Property propertyNull = new Property();

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> this.propertyRepository.save(propertyNull));

        // мдааа ужас
        String expectedMessage = "could not execute statement; SQL [n/a]; constraint [null];";
        String exceptionMessage = exception.getMessage().substring(0, 58);

//        Assertions.assertTrue(exceptionMessage.contains(expectedMessage));
        Assertions.assertEquals(expectedMessage, exceptionMessage);
    }
}
