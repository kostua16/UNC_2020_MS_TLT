package nc.unc.cs.services.bank.controllers;

import feign.FeignException;
import nc.unc.cs.services.bank.exceptions.PaymentRequestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  /**
   * Логгер.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
  /**
   * Стандартное сообщение для фронта.
   */
  private static final String TAX_SERVICE_UNAVAILABLE_MESSAGE = "Tax service unavailable!";

  @ExceptionHandler(value = {FeignException.class})
  public ResponseEntity<Object> serviceUnavailableException(final FeignException fe) {
    LOGGER.error(TAX_SERVICE_UNAVAILABLE_MESSAGE, fe);
    return new ResponseEntity<>(
        TAX_SERVICE_UNAVAILABLE_MESSAGE, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(value = {PaymentRequestNotFoundException.class})
  public ResponseEntity<Object> paymentRequestNotFound(final PaymentRequestNotFoundException pe) {
    LOGGER.error("Search failed!", pe);
    return new ResponseEntity<>(pe.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
