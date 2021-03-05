package nc.unc.cs.services.communal.controllers;

import feign.FeignException;
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
  /** Стандартное сообщение для фронта. */
  private static final String SERVICE_UNAVAILABLE_MESSAGE = "Operation failed!";

  @ExceptionHandler(value = {FeignException.class})
  public ResponseEntity<Object> serviceUnavailableException(final FeignException fe) {
    LOGGER.error(SERVICE_UNAVAILABLE_MESSAGE, fe);
    return new ResponseEntity<>(SERVICE_UNAVAILABLE_MESSAGE, HttpStatus.SERVICE_UNAVAILABLE);
  }
}
