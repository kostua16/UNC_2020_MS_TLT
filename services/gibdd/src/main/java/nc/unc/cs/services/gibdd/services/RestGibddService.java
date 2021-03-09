package nc.unc.cs.services.gibdd.services;

import nc.unc.cs.services.gibdd.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gibdd")
public class RestGibddService {

  private final LogicOfGibddService logic;

  @Value("${gibdd.test:default}") private String testName;

  @Autowired
  public RestGibddService(LogicOfGibddService logic) {
    this.logic = logic;
  }

  @GetMapping(value = "/", produces = "application/json")
  public Iterable<Car> indexCars() {
    return this.logic.getCars();
  }

  @PostMapping(value = "/", consumes = "application/json",
               produces = "application/json")
  public Car
  addCar(@RequestBody final Car car) {
    return this.logic.addCar(car);
  }

  @GetMapping(value = "/test", produces = "text/plain")
  public String getTestName() {
    return this.testName;
  }
}
