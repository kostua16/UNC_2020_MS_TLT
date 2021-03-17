package nc.unc.cs.services.communal.controllers;

import feign.FeignException;
import nc.unc.cs.services.communal.exceptions.PropertyNotFoundException;
import nc.unc.cs.services.communal.exceptions.PropertyTaxNotFoundException;
import nc.unc.cs.services.communal.exceptions.PropertyTaxValueNotFoundException;
import nc.unc.cs.services.communal.exceptions.UtilitiesPriceListNotFoundException;
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
  private static final String SERVICE_UNAVAILABLE_MESSAGE = "Operation failed!";

  @ExceptionHandler(value = {FeignException.class})
  public ResponseEntity<Object> serviceUnavailableException(
      final FeignException fe
  ) {
    LOGGER.error(SERVICE_UNAVAILABLE_MESSAGE, fe);
    return new ResponseEntity<>(SERVICE_UNAVAILABLE_MESSAGE, HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(value = {PropertyNotFoundException.class})
  public ResponseEntity<Object> propertyNotFoundException(
      final PropertyNotFoundException pe
  ) {
    LOGGER.error("Property Not Found!", pe);
    return new ResponseEntity<>(pe.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {PropertyTaxNotFoundException.class})
  public ResponseEntity<Object> propertyTaxNotFoundException(
      final PropertyTaxNotFoundException pt
  ) {
    LOGGER.error("Property Tax Not Found!", pt);
    return new ResponseEntity<>(pt.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {PropertyTaxValueNotFoundException.class})
  public ResponseEntity<Object> propertyTaxValueNotFoundException(
      final PropertyTaxValueNotFoundException ptv
  ) {
    LOGGER.error("Property Tax Value Not Found!", ptv);
    return new ResponseEntity<>(ptv.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {UtilitiesPriceListNotFoundException.class})
  public ResponseEntity<Object> propertyTaxValueNotFoundException(
      final UtilitiesPriceListNotFoundException upe
  ) {
    LOGGER.error("Utilities Price List Not Found!", upe);
    return new ResponseEntity<>(upe.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
