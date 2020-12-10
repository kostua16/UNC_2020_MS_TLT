package nc.unc.cs.services.gibdd.services;

import java.util.Date;
import nc.unc.cs.services.gibdd.entities.Car;
import nc.unc.cs.services.gibdd.integration.LogEntry;
import nc.unc.cs.services.gibdd.integration.LoggingService;
import nc.unc.cs.services.gibdd.repositories.CarsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service representing GIBDD social services.
 * @since 0.1.0
 */
@Service
public class LogicOfGibddService {

    private final LoggingService logger;

    private CarsRepo carsRepo;

    @Autowired
    public LogicOfGibddService(
        final CarsRepo carsRepo,
        final LoggingService logger
        ) {
        this.carsRepo = carsRepo;
        this.logger = logger;
    }

    public Iterable<Car> getCars() {
        return this.carsRepo.findAll();
    }

    public Car addCar(Car car) {
        final Car saved = this.carsRepo.save(car);
        this.logger.addLog(
            new LogEntry(
                new Date(),
                "gibdd",
                String.format("Added new car: %s", saved.toString())
            )
        );
        return saved;
    }
}
