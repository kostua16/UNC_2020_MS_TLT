package nc.unc.cs.services.gibdd.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import nc.unc.cs.services.common.clients.gibdd.CarDto;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.common.services.ModelMapperConfiguration;
import nc.unc.cs.services.gibdd.entities.Car;
import nc.unc.cs.services.gibdd.repositories.CarsRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests for Gibdd service
 * Documented automatically using RestDoc.
 *   https://docs.spring.io/spring-restdocs/docs/current-SNAPSHOT/reference/html5/
 */
@Slf4j
@WebMvcTest(
    {GibddService.class, ObjectMapper.class, ModelMapperConfiguration.class})
@AutoConfigureRestDocs
class GibddServiceTest {

  private static final FieldDescriptor OWNER_DESCR =
      fieldWithPath("owner").type(String.class).description("Owner of the car");

  private static final FieldDescriptor CAR_NUM_DESCR =
      fieldWithPath("number")
          .type(String.class)
          .description("Number of the car");

  private static final FieldDescriptor[] CAR_DESCR = new FieldDescriptor[] {
      GibddServiceTest.OWNER_DESCR, GibddServiceTest.CAR_NUM_DESCR};

  private static final ResponseFieldsSnippet CAR_RESP =
      responseFields(GibddServiceTest.CAR_DESCR);
  private static final ResponseFieldsSnippet CARS_RESP =
      responseFields(fieldWithPath("[]").description("Cars"))
          .andWithPrefix("[].", GibddServiceTest.CAR_DESCR);

  private static final RequestFieldsSnippet CAR_REQ =
      requestFields(GibddServiceTest.CAR_DESCR);

  @MockBean private CarsRepo repo;

  @MockBean private LoggingService logs;

  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper omp;

  @Autowired private ModelMapper mmp;

  @Test
  void ownedCars() throws Exception {
    String owner = "111";
    List<Car> cars =
        ImmutableList.<Car>builder().add(new Car(1, "111", owner)).build();
    when(repo.findCarsByOwner(owner)).thenReturn(cars);
    mvc.perform(get("/gibdd/owned/{owner}", owner)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("ownedCars", GibddServiceTest.CARS_RESP))
        .andExpect(jsonPath("$[0].owner", Matchers.is(owner)))
        .andExpect(status().isOk());
  }

  @Test
  void findCar() throws Exception {
    String number = "111";
    List<Car> cars =
        ImmutableList.<Car>builder().add(new Car(1, number, "111")).build();
    when(repo.findCarsByNumber(number)).thenReturn(cars);
    mvc.perform(get("/gibdd/cars/{number}", number)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("findCar", GibddServiceTest.CAR_RESP))
        .andExpect(jsonPath("$.number", Matchers.is(number)))
        .andExpect(status().isOk());
  }

  @Test
  void findCarNotFound() throws Exception {
    String number = "111";
    when(repo.findCarsByNumber(number)).thenReturn(Collections.emptyList());
    mvc.perform(get("/gibdd/cars/{number}", number)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("findCarNotFound"))
        .andExpect(status().isNotFound());
  }

  @Test
  void addCar() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class)))
        .thenAnswer((Answer<Car>)inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber()))
        .thenReturn(Collections.emptyList());
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(put("/gibdd/cars")
                    .content(this.omp.writeValueAsString(
                        this.mmp.map(car, CarDto.class)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("addCar", GibddServiceTest.CAR_RESP))
        .andExpect(jsonPath("$.owner", Matchers.is(car.getOwner())))
        .andExpect(status().isCreated());
  }

  @Test
  void addCarDuplicated() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class)))
        .thenAnswer((Answer<Car>)inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber()))
        .thenReturn(Collections.singletonList(car));
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(put("/gibdd/cars")
                    .content(this.omp.writeValueAsString(
                        this.mmp.map(car, CarDto.class)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("addCarDuplicated"))
        .andExpect(status().isAlreadyReported());
  }

  @Test
  void updateCar() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class)))
        .thenAnswer((Answer<Car>)inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber()))
        .thenReturn(Collections.singletonList(car));
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(post("/gibdd/cars")
                    .content(this.omp.writeValueAsString(
                        this.mmp.map(car, CarDto.class)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("updateCar", GibddServiceTest.CAR_REQ,
                        GibddServiceTest.CAR_RESP))
        .andExpect(jsonPath("$.owner", Matchers.is(car.getOwner())))
        .andExpect(status().isOk());
  }

  @Test
  void updateCarNotFound() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class)))
        .thenAnswer((Answer<Car>)inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber()))
        .thenReturn(Collections.emptyList());
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(post("/gibdd/cars")
                    .content(this.omp.writeValueAsString(
                        this.mmp.map(car, CarDto.class)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
        .andDo(document("updateCarNotFound"))
        .andExpect(status().isNotFound());
  }
}
