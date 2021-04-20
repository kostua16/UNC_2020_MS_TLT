package nc.unc.cs.services.account.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.account.controllers.dto.RegistrationDto;
import nc.unc.cs.services.account.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AuthController.class})
@Import(ObjectMapper.class)
class RegistrationWebTest {
  private static final String REGISTER_CONTROLLER_MAPPING = "http://localhost:8888/auth/sign-up";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private AuthService authService;

  private RegistrationDto createRegistrationDto() {
    return RegistrationDto.builder()
        .username("username")
        .password("password")
        .email("hello@gmail.com")
        .build();
  }

  @Test
  void registerSuccessfulTest() throws Exception {
    final RegistrationDto registrationDto = this.createRegistrationDto();
    given(this.authService.register(registrationDto))
        .willReturn(ResponseEntity.ok(registrationDto.getUsername()));

    this.mockMvc
        .perform(
            post(REGISTER_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registrationDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
