package nc.unc.cs.services.account.controllers;

import java.util.List;
import nc.unc.cs.services.account.controllers.dto.AuthResponse;
import nc.unc.cs.services.account.controllers.dto.LoginDto;
import nc.unc.cs.services.account.controllers.dto.RegistrationDto;
import nc.unc.cs.services.account.entities.Account;
import nc.unc.cs.services.account.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "sign-up", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> register(
      @Validated @RequestBody final RegistrationDto registrationDto) {
    return this.authService.register(registrationDto);
  }

  @PostMapping(value = "sign-in", consumes = "application/json", produces = "application/json")
  public ResponseEntity<AuthResponse> login(@Validated @RequestBody final LoginDto loginDto) {
    return this.authService.login(loginDto);
  }

  @GetMapping(produces = "application/json")
  public List<Account> getAllAccounts() {
    return this.authService.getAllAccounts();
  }

  @PutMapping(value = "role/{username}", produces = "application/json")
  public ResponseEntity<AuthResponse> changeRoleToAdmin(@PathVariable final String username) {
    return this.authService.changeRoleToAdmin(username);
  }
}
