package nc.unc.cs.services.account.services;

import java.util.List;
import feign.FeignException;
import nc.unc.cs.services.account.controllers.dto.LoginDto;
import nc.unc.cs.services.account.controllers.dto.RegistrationDto;
import nc.unc.cs.services.account.entities.Account;
import nc.unc.cs.services.account.exceptions.AccountNotFoundException;
import nc.unc.cs.services.account.exceptions.RegistrationException;
import nc.unc.cs.services.account.repositories.AccountRepository;
import nc.unc.cs.services.common.clients.passport.Citizen;
import nc.unc.cs.services.common.clients.passport.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

  private final AccountRepository accountRepository;
  private final PassportService passportService;

  @Autowired
  public AuthService(
      final AccountRepository accountRepository,
      final PassportService passportService
  ) {
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
   * @return http-статус, в теле которого находится логин
   *      зарегестрированного гражданина
   * @throws RegistrationException если имя пользователя уже зарегестрировано
   */
  public ResponseEntity<String> register(final RegistrationDto registrationDto) {
    final Account account = this.accountRepository.findAccountByUsername(registrationDto.getUsername());
    if (account == null) {
      final Account newAccount = Account
          .builder()
          .username(registrationDto.getUsername())
          .password(registrationDto.getPassword())
          .build();
      this.accountRepository.save(newAccount);
      final Citizen citizen = Citizen
          .builder()
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
   */
  public ResponseEntity<Long> login(final LoginDto loginDto) {
    final Account account = this.accountRepository
        .findAccountByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
    if (account == null) {
      throw new AccountNotFoundException(loginDto.getUsername(), loginDto.getPassword());
    }
    return ResponseEntity.ok(account.getCitizenId());
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

}
