package nc.unc.cs.services;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.entities.Car;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RestGibddService.class)
@Import(ObjectMapper.class)
class Project2020GibddRestServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogicOfGibddService logic;

    @Autowired
    private ObjectMapper mapper;

    @ParameterizedTest(
        name = "addCarTest({0}, {1}) = OK"
    )
    @CsvSource({
        "111, user111",
        "112, user112",
    })
    void addCarTest(String number, String owner) throws Exception {
        final Car car = new Car(number, owner);
        Mockito.when(this.logic.addCar(Mockito.eq(car))).thenReturn(car);
        mvc
            .perform(
                MockMvcRequestBuilders
                    .post("/gibdd/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(car))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                MockMvcResultMatchers.status().isOk()
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.number").value(number)
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.owner").value(owner)
            );
    }

    @Test
    void getCars() throws Exception {
        List<Car> list = new ArrayList<>();
        final Car first = new Car("111", "user111");
        final Car second = new Car("112", "user112");
        list.add(first);
        list.add(second);
        Mockito.when(this.logic.getCars()).thenReturn(list);
        mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/gibdd/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                MockMvcResultMatchers.status().isOk()
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$[1].number")
                    .value(Matchers.is(second.getNumber()))
            ).andExpect(
            MockMvcResultMatchers
                .jsonPath("$[1].owner")
                .value(Matchers.is(second.getOwner()))
        );
    }

}
