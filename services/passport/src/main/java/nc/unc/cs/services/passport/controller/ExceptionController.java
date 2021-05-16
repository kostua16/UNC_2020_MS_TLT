package nc.unc.cs.services.passport.controller;

import nc.unc.cs.services.passport.exceptions.DomesticPassportNotFoundException;
import nc.unc.cs.services.passport.exceptions.InternationalPassportNotFoundException;
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

  @ExceptionHandler(value = {DomesticPassportNotFoundException.class})
  public ResponseEntity<Object> propertyNotFoundException(final DomesticPassportNotFoundException dpe) {
    LOGGER.error("Domestic passport not Found!", dpe);
    return new ResponseEntity<>(dpe.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {InternationalPassportNotFoundException.class})
  public ResponseEntity<Object> propertyNotFoundException(final InternationalPassportNotFoundException ipe) {
    LOGGER.error("International passport not Found!", ipe);
    return new ResponseEntity<>(ipe.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
