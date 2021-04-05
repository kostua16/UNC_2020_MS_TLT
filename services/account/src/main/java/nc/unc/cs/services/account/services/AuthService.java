package nc.unc.cs.services.account.services;

import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.account.controllers.dto.AuthResponse;
import nc.unc.cs.services.account.controllers.dto.LoginDto;
import nc.unc.cs.services.account.controllers.dto.RegistrationDto;
import nc.unc.cs.services.account.entities.Account;
import nc.unc.cs.services.account.entities.Roles;
import nc.unc.cs.services.account.exceptions.AccountNotFoundException;
import nc.unc.cs.services.account.exceptions.IncorrectPasswordException;
import nc.unc.cs.services.account.exceptions.RegistrationException;
import nc.unc.cs.services.account.repositories.AccountRepository;
import nc.unc.cs.services.common.clients.passport.Citizen;
import nc.unc.cs.services.common.clients.passport.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

  private final BCryptPasswordEncoder encoder;
  private final AccountRepository accountRepository;
  private final PassportService passportService;

  @Autowired
  public AuthService(
      final BCryptPasswordEncoder encoder,
      final AccountRepository accountRepository,
      final PassportService passportService
  ) {
    this.encoder = encoder;
    this.accountRepository = accountRepository;
    this.passportService = passportService;
  }

  /**
   * Отправляет запрос на оформление паспорта гражданина.
   *
   * @param citizen информация для оформления паспорта
   * @throws FeignException если Паспортный сервис не отвечает
   */
  private void createPassport(final Citizen citizen) throws FeignException {
    this.passportService.registerDomesticPassport(citizen);
  }

  /**
   * Регистрация пользователя.
   *
   * @param registrationDto данные о гражданине
   * @return http-статус, в теле которого находится логин зарегестрированного гражданина
   * @throws RegistrationException если имя пользователя уже зарегестрировано
   */
  public ResponseEntity<String> register(final RegistrationDto registrationDto) {
    final Account account =
        this.accountRepository.findAccountByUsername(registrationDto.getUsername());
    if (account == null) {
      final Account newAccount =
          Account.builder()
              .username(registrationDto.getUsername())
              .password(encoder.encode(registrationDto.getPassword()))
              .build();
      this.accountRepository.save(newAccount);
      final Citizen citizen =
          Citizen.builder()
              .citizenId(newAccount.getCitizenId())
              .name(registrationDto.getName())
              .surname(registrationDto.getSurname())
              .dateOfBirth(registrationDto.getDateOfBirth())
              .registration(registrationDto.getRegistration())
              .build();
      this.createPassport(citizen);
      newAccount.setIsActive(true);
      this.accountRepository.save(newAccount);
      return ResponseEntity.ok(newAccount.getUsername());
    } else {
      throw new RegistrationException(registrationDto.getUsername());
    }
  }

  /**
   * Аунтификация гражданина.
   *
   * @param loginDto логин и пароль гражданина
   * @return http-статус, в теле которого находится идентификатор гражданина
   * @throws AccountNotFoundException если такого аккаунта не существует
   * @throws IncorrectPasswordException если неверный пароль
   */
  public ResponseEntity<AuthResponse> login(final LoginDto loginDto) {
    final Account account = this.accountRepository.findAccountByUsername(loginDto.getUsername());
    LOGGER.info("INFO: {}", account);
    if (account == null) {
      throw new AccountNotFoundException(loginDto.getUsername(), loginDto.getPassword());
    } else if (encoder.matches(loginDto.getPassword(), account.getPassword())) {
      final AuthResponse authResponse =
          AuthResponse.builder().citizenId(account.getCitizenId()).role(account.getRole()).build();
      return ResponseEntity.ok(authResponse);
    } else {
      throw new IncorrectPasswordException(loginDto.getUsername());
    }
  }

  /**
   * Возвращает список все аккаунтов. (ВРЕМЕННЫЙ МЕТОД!!!)
   *
   * @return список аккаунтов
   */
  @Deprecated
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @Deprecated
  public ResponseEntity<AuthResponse> changeRoleToAdmin(final String username) {
    final Account account = this.accountRepository.findAccountByUsername(username);
    if (account == null) {
      throw new AccountNotFoundException(username, "");
    } else {
      account.setRole(Roles.ROLE_ADMIN);
      this.accountRepository.save(account);
      final AuthResponse authResponse =
          AuthResponse.builder().citizenId(account.getCitizenId()).role(account.getRole()).build();
      return ResponseEntity.ok(authResponse);
    }
  }
}
