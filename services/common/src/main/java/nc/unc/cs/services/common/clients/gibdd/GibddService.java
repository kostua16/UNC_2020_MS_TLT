package nc.unc.cs.services.common.clients.gibdd;

import javax.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GIBDD", path = "gibdd", url = "${app.gibdd-url:localhost:8080}")
@ConditionalOnMissingClass("nc.unc.cs.services.gibdd.services.GibddService")
public interface GibddService {

  @GetMapping(value = "owned/{owner}", produces = "application/json")
  Iterable<CarDto> ownedCars(@PathVariable("owner") @Validated @NotNull String owner);

  @GetMapping(value = "cars/{number}", produces = "application/json")
  ResponseEntity<CarDto> findCar(@PathVariable("number") @Validated @NotNull String number);

  @PutMapping(value = "cars", consumes = "application/json", produces = "application/json")
  ResponseEntity<CarDto> addCar(@RequestBody @Validated @NotNull CarDto dto);

  @PostMapping(value = "cars", consumes = "application/json", produces = "application/json")
  ResponseEntity<CarDto> updateCar(@RequestBody @Validated CarDto dto);
}
