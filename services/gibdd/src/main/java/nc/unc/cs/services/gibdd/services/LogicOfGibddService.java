package nc.unc.cs.services.gibdd.services;

import java.util.Date;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.gibdd.entities.Car;
import nc.unc.cs.services.gibdd.repositories.CarsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service representing GIBDD social services.
 *
 * @since 0.1.0
 */
@Service
public class LogicOfGibddService {

  private final LoggingService loggingService;

  private final Logger logger = LoggerFactory.getLogger(LogicOfGibddService.class);

  private CarsRepo carsRepo;

  @Autowired
  public LogicOfGibddService(final CarsRepo carsRepo, final LoggingService loggingService) {
    this.carsRepo = carsRepo;
    this.loggingService = loggingService;
  }

  public Iterable<Car> getCars() {
    return this.carsRepo.findAll();
  }

  public Car addCar(Car car) {
    this.logger.debug("before adding car: {}", car);
    final Car saved = this.carsRepo.save(car);
    this.logger.debug("after adding car: {}", saved);
    final LogEntry lgm =
        LogEntry.builder()
            .service("gibdd")
            .created(new Date())
            .message(String.format("Added new car: %s", saved.toString()))
            .build();
    try {
      this.logger.debug("before sending log: {}", lgm);
      final LogEntry storedLogEntry = this.loggingService.addLog(lgm);
      this.logger.debug("after sending log: {}", storedLogEntry);
    } catch (Exception exception) {
      this.logger.error("Failed to send log", exception);
    }

    return saved;
  }
}
