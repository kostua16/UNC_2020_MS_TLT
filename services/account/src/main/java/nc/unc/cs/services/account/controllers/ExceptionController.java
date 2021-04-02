package nc.unc.cs.services.account.controllers;

import feign.FeignException;
import nc.unc.cs.services.account.exceptions.AccountNotFoundException;
import nc.unc.cs.services.account.exceptions.IncorrectPasswordException;
import nc.unc.cs.services.account.exceptions.RegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  /** Логгер. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

  @ExceptionHandler(value = {FeignException.class})
  public ResponseEntity<Object> loginException(final FeignException fe) {
    LOGGER.error("Passport service unavailable! \n", fe);
    return new ResponseEntity<>(fe.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(value = {AccountNotFoundException.class})
  public ResponseEntity<Object> loginException(final AccountNotFoundException ae) {
    LOGGER.error("Failed to authenticate! \n", ae);
    return new ResponseEntity<>(ae.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {RegistrationException.class})
  public ResponseEntity<Object> registrationException(final RegistrationException re) {
    LOGGER.error("An account with the same name already exists! \n", re);
    return new ResponseEntity<>(re.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {IncorrectPasswordException.class})
  public ResponseEntity<Object> registrationException(final IncorrectPasswordException ie) {
    LOGGER.error("Incorrect password! \n", ie);
    return new ResponseEntity<>(ie.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
