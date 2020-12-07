package nc.unc.cs.services;

import java.util.List;
import nc.unc.cs.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gibdd")
public class RestGibddService {

    private final LogicOfGibddService logic;

    @Autowired
    public RestGibddService(LogicOfGibddService logic) {
        this.logic = logic;
    }

    @GetMapping(value = "/", produces = "application/json")
    public List<Car> indexCars() {
        return this.logic.getCars();
    }

    @PostMapping(value = "/", produces = "application/json")
    public boolean addCar(@RequestBody final Car car) {
        this.logic.addCar(car);
        return true;
    }
}
