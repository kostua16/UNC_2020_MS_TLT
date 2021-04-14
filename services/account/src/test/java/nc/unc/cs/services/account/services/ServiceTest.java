package nc.unc.cs.services.account.services;

import nc.unc.cs.services.account.controllers.dto.AuthResponse;
import nc.unc.cs.services.account.controllers.dto.LoginDto;
import nc.unc.cs.services.account.controllers.dto.RegistrationDto;
import nc.unc.cs.services.account.entities.Account;
import nc.unc.cs.services.account.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ServiceTest {

  @InjectMocks
  private AuthService authService;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private BCryptPasswordEncoder encoder;

  private LoginDto createLoginDto() {
    return LoginDto.builder().username("username").password("password").build();
  }

  private Account createAccount(final String username, final String password) {
    return Account
        .builder()
        .citizenId(1L)
        .username(username)
        .password(password)
        .email("hello@gmail.com")
        .build();
  }

  private RegistrationDto createRegistrationDto() {
    return RegistrationDto
        .builder()
        .username("username")
        .password("password")
        .email("hello@gmail.com")
        .build();
  }

  @Test
  void loginTest() {
    final LoginDto loginDto = this.createLoginDto();
    final Account account = this.createAccount(loginDto.getUsername(), loginDto.getPassword());
    account.setIsActive(true);
    //    account.setPassword(encoder.encode(account.getPassword()));
    given(this.accountRepository.findAccountByUsername(loginDto.getUsername())).willReturn(account);
    given(this.encoder.matches(loginDto.getPassword(), account.getPassword())).willReturn(true);
    final ResponseEntity<AuthResponse> response = this.authService.login(loginDto);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void registrationTest() {
    final RegistrationDto registrationDto = this.createRegistrationDto();

    given(this.accountRepository.findAccountByUsername(registrationDto.getUsername()))
        .willReturn(null);
    given(this.accountRepository.save(any())).willAnswer(mock -> mock.getArgument(0));
    given(this.encoder.encode(registrationDto.getPassword()))
        .willReturn(registrationDto.getPassword());

    given(this.accountRepository.save(any())).willAnswer(mock -> mock.getArgument(0));

    final ResponseEntity<String> response = this.authService.register(registrationDto);

    Assertions.assertAll(
        () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> Assertions.assertEquals(registrationDto.getUsername(), response.getBody()));
  }
}
