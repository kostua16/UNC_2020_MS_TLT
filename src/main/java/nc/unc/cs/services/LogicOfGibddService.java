package nc.unc.cs.services;

import java.util.ArrayList;
import java.util.List;
import nc.unc.cs.entities.Car;
import org.springframework.stereotype.Service;

/**
 * Service representing GIBDD social services.
 * @since 0.1.0
 */
@Service
public class LogicOfGibddService {

    private final List<Car> cars;

    public LogicOfGibddService() {
        this.cars = new ArrayList<>();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }
}
