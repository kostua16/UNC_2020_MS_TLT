package nc.unc.cs.services.communal.services.integrations.background;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.communal.entities.Property;
import nc.unc.cs.services.communal.entities.PropertyTaxValue;
import nc.unc.cs.services.communal.repositories.PropertyRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxRepository;
import nc.unc.cs.services.communal.repositories.PropertyTaxValueRepository;
import nc.unc.cs.services.communal.repositories.UtilityBillRepository;
import nc.unc.cs.services.communal.services.BackgroundTaskService;
import nc.unc.cs.services.communal.services.BankIntegrationService;
import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
class BackgroundTaskServiceTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskServiceTest.class);

  //  @Value("${communal.property.tax-percent}") // не работает
  private Integer taxPercent = 10;

  //  @Value("${communal.service-id.property-tax}")
  private Long serviceId = 20L;

  //  @Value("${communal.background.job.tax-period}")
  private Integer taxPeriod = 1;

  @Mock private PropertyRepository propertyRepository;
  @Mock private PropertyTaxRepository propertyTaxRepository;
  @Mock private PropertyTaxValueRepository propertyTaxValueRepository;
  @Mock private UtilityBillRepository utilityBillRepository;
  @Mock private BankIntegrationService bankIntegrationService;
  @Mock private LoggingService logging;
  @InjectMocks private BackgroundTaskService backgroundTaskService;

  private Property createProperty() {
    return Property.builder()
        .propertyId(1L)
        .region(" samara ")
        .city("samara")
        .street("main")
        .house("12d")
        .apartment("11")
        .apartmentSize(1000)
        .citizenId(111L)
        .build();
  }

  private List<Property> createProperties() {
    final List<Property> properties = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      final Property property = this.createProperty();
      property.setPropertyId(property.getPropertyId() + i);
      properties.add(property);
    }
    return properties;
  }

  private PropertyTaxValue createPropertyTaxValue() {
    return PropertyTaxValue.builder()
        .region("samara")
        .pricePerSquareMeter(1000)
        .cadastralValue(10)
        .propertyTaxValueId(1L)
        .build();
  }

  @Test
  void reportDate() {
    final Property property = this.createProperty();
    final List<Property> properties = this.createProperties();
    final PropertyTaxValue propertyTaxValue = this.createPropertyTaxValue();
    final Integer amount =
        this.backgroundTaskService.calculatePropertyTaxAmount(
            Double.valueOf(property.getApartmentSize()),
            Double.valueOf(propertyTaxValue.getPricePerSquareMeter()),
            Double.valueOf(propertyTaxValue.getCadastralValue()));
    ReflectionTestUtils.setField(backgroundTaskService, "taxPercent", 10);
    ReflectionTestUtils.setField(backgroundTaskService, "serviceId", 20L);
    ReflectionTestUtils.setField(backgroundTaskService, "taxPeriod", 1);
    final Date beforeDate = DateUtils.addDays(new Date(), -taxPeriod);
    given(this.propertyRepository.findFirstByPropertyTaxDateBefore(any())).willReturn(property);
    given(
            this.propertyRepository.findFirst3ByPropertyTaxDateBeforeAndRegion(
                beforeDate, property.getRegion()))
        .willReturn(properties);
    given(this.propertyTaxValueRepository.findPropertyTaxValueByRegion(property.getRegion()))
        .willReturn(propertyTaxValue);
    given(logging.addLog(any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    given(
            this.bankIntegrationService.bankRequest(
                serviceId, property.getCitizenId(), amount, taxPercent))
        .willReturn(55L);
    given(this.propertyTaxRepository.save(any()))
        .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    given(this.propertyRepository.save(any()))
        .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    //    Assertions.assertDoesNotThrow(
    //        F.class,
    //        () -> );
    this.backgroundTaskService.reportDate();
  }
}
