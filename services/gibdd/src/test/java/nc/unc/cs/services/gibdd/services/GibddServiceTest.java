package nc.unc.cs.services.gibdd.services;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.common.services.ModelMapperConfiguration;
import nc.unc.cs.services.gibdd.entities.Car;
import nc.unc.cs.services.gibdd.entities.CarDto;
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
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

/**
 * Tests for Gibdd service
 * Documented automatically using RestDoc.
 *   https://docs.spring.io/spring-restdocs/docs/current-SNAPSHOT/reference/html5/
 */
@Slf4j
@WebMvcTest({GibddService.class, ObjectMapper.class, ModelMapperConfiguration.class})
@AutoConfigureRestDocs
class GibddServiceTest {

  @MockBean
  private CarsRepo repo;

  @MockBean
  private LoggingService logs;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper omp;

  @Autowired
  private ModelMapper mmp;

  @Test
  void ownedCars() throws Exception {
    String owner = "111";
    List<Car> cars = ImmutableList.<Car>builder().add(new Car(1, "111", owner)).build();
    when(repo.findCarsByOwner(owner)).thenReturn(cars);
    mvc.perform(get("/gibdd/owned/{owner}", owner).accept(MediaType.APPLICATION_JSON))
        .andDo(document("ownedCars"))
        .andExpect(jsonPath("$[0].owner", Matchers.is(owner)))
        .andExpect(status().isOk());
  }

  @Test
  void findCar() throws Exception {
    String number = "111";
    List<Car> cars = ImmutableList.<Car>builder().add(new Car(1, number, "111")).build();
    when(repo.findCarsByNumber(number)).thenReturn(cars);
    mvc.perform(get("/gibdd/cars/{number}", number).accept(MediaType.APPLICATION_JSON))
        .andDo(document("findCar"))
        .andExpect(jsonPath("$.number", Matchers.is(number)))
        .andExpect(status().isOk());
  }

  @Test
  void addCar() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class))).thenAnswer((Answer<Car>) inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber())).thenReturn(Collections.emptyList());
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(
        put("/gibdd/cars").content(this.omp.writeValueAsString(this.mmp.map(car, CarDto.class)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(document("addCar"))
        .andExpect(jsonPath("$.owner", Matchers.is(car.getOwner())))
        .andExpect(status().isCreated());
  }

  @Test
  void updateCar() throws Exception {
    Car car = new Car(1, "ab111ab", "111");
    when(repo.save(any(Car.class))).thenAnswer((Answer<Car>) inv -> inv.getArgument(0, Car.class));
    when(repo.findCarsByNumber(car.getNumber())).thenReturn(Collections.singletonList(car));
    when(logs.addLog(any())).thenReturn(null);
    mvc.perform(
        post("/gibdd/cars").content(this.omp.writeValueAsString(this.mmp.map(car, CarDto.class)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(document("updateCar"))
        .andExpect(jsonPath("$.owner", Matchers.is(car.getOwner())))
        .andExpect(status().isOk());
  }
}
