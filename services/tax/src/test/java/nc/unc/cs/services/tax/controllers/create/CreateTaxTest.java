package nc.unc.cs.services.tax.controllers.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nc.unc.cs.services.common.clients.tax.CreationTax;
import nc.unc.cs.services.tax.controllers.TaxController;
import nc.unc.cs.services.tax.services.TaxService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Tests for Tax service Documented automatically using RestDoc.
 * https://docs.spring.io/spring-restdocs/docs/current-SNAPSHOT/reference/html5/
 */
@Slf4j
@WebMvcTest({TaxController.class, ObjectMapper.class})
@Import(ObjectMapper.class)
@AutoConfigureRestDocs
class CreateTaxTest {
  private static final FieldDescriptor TAX_AMOUNT_DESCR =
      fieldWithPath("taxAmount").type(Integer.class).description("Tax amount.");
  private static final FieldDescriptor SERVICE_ID_DESCR =
      fieldWithPath("serviceId").type(Long.class).description("Id of the service.");
  private static final FieldDescriptor CITIZEN_ID_DESCR =
      fieldWithPath("citizenId").type(Long.class).description("Id of the citizen.");
//  private static final FieldDescriptor TAX_ID_DESCR =
//      fieldWithPath("taxId").description("Id of the tax.");
  private static final FieldDescriptor[] TAX_DESCR =
      new FieldDescriptor[]{
          CreateTaxTest.TAX_AMOUNT_DESCR,
          CreateTaxTest.SERVICE_ID_DESCR,
          CreateTaxTest.CITIZEN_ID_DESCR
      };

//  private static final ResponseFieldsSnippet TAX_RESP = responseFields(CreateTaxTest.TAX_DESCR);
  private static final RequestFieldsSnippet TAX_REQ = requestFields(CreateTaxTest.TAX_DESCR);

  private static final String TAX_CONTROLLER_MAPPING = "http://localhost:8082/tax";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private TaxService taxService;

  private CreationTax getCreationTax() {
    return CreationTax.builder().serviceId(20L).citizenId(111L).taxAmount(1000).build();
  }

  @Test
  void createTax() throws Exception {
    final CreationTax creationTax = this.getCreationTax();

    when(this.taxService.createTax(
        creationTax.getServiceId(), creationTax.getCitizenId(), creationTax.getTaxAmount()))
        .thenReturn(1L);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(document("createTax", CreateTaxTest.TAX_REQ))
        .andExpect(status().isOk());
//        .andExpect(jsonPath("$.taxId", Matchers.is(1L)));
  }

  @Test
  void createTaxNullServiceIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setServiceId(null);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTaxSmallerServiceIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setServiceId(0L);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTaxNullCitizenIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setCitizenId(null);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTaxSmallerCitizenIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setCitizenId(0L);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTaxNullTaxAmountIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setTaxAmount(null);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTaxSmallerTaxAmountIdTest() throws Exception {
    final CreationTax creationTax = this.getCreationTax();
    creationTax.setTaxAmount(0);

    this.mockMvc
        .perform(
            post(TAX_CONTROLLER_MAPPING + "/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creationTax)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
