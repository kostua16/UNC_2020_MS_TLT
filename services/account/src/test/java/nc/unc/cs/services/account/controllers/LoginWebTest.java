package nc.unc.cs.services.account.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nc.unc.cs.services.account.controllers.dto.AuthResponse;
import nc.unc.cs.services.account.controllers.dto.LoginDto;
import nc.unc.cs.services.account.entities.Roles;
import nc.unc.cs.services.account.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AuthController.class})
@Import(ObjectMapper.class)
class LoginWebTest {
  private static final String LOGIN_CONTROLLER_MAPPING = "http://localhost:8888/auth/sign-in";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private AuthService authService;

  private LoginDto createLoginDto() {
    return LoginDto.builder().username("username").password("password").build();
  }

  @Test
  void loginSuccessfulTest() throws Exception {
    final LoginDto loginDto = this.createLoginDto();
    final AuthResponse authResponse = AuthResponse
        .builder()
        .citizenId(1L)
        .role(Roles.ROLE_USER)
        .build();

    given(this.authService.login(loginDto)).willReturn(ResponseEntity.ok(authResponse));

    this.mockMvc
        .perform(
            post(LOGIN_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @ParameterizedTest
  @ValueSource(strings = {"   ", "sss ", "sssssssssssssssssssssssssssssssssssssssss"})
  void invalidUsernameLoginTest(final String word) throws Exception {
    final LoginDto loginDto = this.createLoginDto();
    loginDto.setUsername(word);

    this.mockMvc
        .perform(
            post(LOGIN_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"   ", "ssss", "sssssssssssssssssssssssssssssssssssssssss"})
  void invalidPasswordLoginTest(final String word) throws Exception {
    final LoginDto loginDto = this.createLoginDto();
    loginDto.setPassword(word);

    this.mockMvc
        .perform(
            post(LOGIN_CONTROLLER_MAPPING)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
