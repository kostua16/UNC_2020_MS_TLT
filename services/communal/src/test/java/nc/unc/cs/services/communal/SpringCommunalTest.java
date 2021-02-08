package nc.unc.cs.services.communal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = CommunalApplication.class)
//@DataJpaTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = {RegistrationService.class})
public class SpringCommunalTest {


//    @Autowired
//    private RegistrationService registrationService;

//    private static Logger logger = LoggerFactory.getLogger(Service.class);

//    @BeforeAll
//    public static void beforeAll() {
//        logger.debug("beforeAll");
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        logger.debug("beforeEach");
//    }
//
//    @AfterEach
//    public void afterEach() {
//        logger.debug("afterEach");
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        logger.debug("afterAll");
//    }

//    @Test
//    public void checkCalculation() {
//        Registration registration = new Registration();
//        registration.setRegion("samara");
//        registration.setStreet("sss");
//        registration.setCity("sss");
//        registration.setHouse("sss");
//        registration.setApartment("sss");
//        registration.setCitizenId(111L);
//
//        ResponseEntity<Registration> resp = this.registrationService.addRegistration(registration);
//        logger.debug("resp: {}", resp.getBody());
//
//    }
}
