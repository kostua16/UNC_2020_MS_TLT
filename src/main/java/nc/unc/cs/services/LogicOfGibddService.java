package nc.unc.cs.services;

import nc.unc.cs.entities.Car;
import nc.unc.cs.repositories.CarsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service representing GIBDD social services.
 * @since 0.1.0
 */
@Service
public class LogicOfGibddService {

    private CarsRepo carsRepo;

    @Autowired
    public LogicOfGibddService(final CarsRepo carsRepo) {
        this.carsRepo = carsRepo;
    }

    public Iterable<Car> getCars() {
        return this.carsRepo.findAll();
    }

    public Car addCar(Car car) {
        return this.carsRepo.save(car);
    }
}
