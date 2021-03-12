package nc.unc.cs.services.gibdd.services;

import com.google.common.collect.ImmutableList;
import feign.FeignException;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import nc.unc.cs.services.common.clients.gibdd.CarDto;
import nc.unc.cs.services.common.clients.logging.LogEntry;
import nc.unc.cs.services.common.clients.logging.LoggingService;
import nc.unc.cs.services.gibdd.entities.Car;
import nc.unc.cs.services.gibdd.repositories.CarsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gibdd")
@Slf4j
public class GibddService {

  private final LoggingService loggingService;

  private CarsRepo repo;

  private final ModelMapper mapper;

  @Autowired
  public GibddService(
      final CarsRepo repo, final LoggingService loggingService, final ModelMapper mapper) {
    this.mapper = mapper;
    this.repo = repo;
    this.loggingService = loggingService;
  }

  @GetMapping(value = "owned/{owner}", produces = "application/json")
  public Iterable<CarDto> ownedCars(@PathVariable("owner") @Validated @NotNull String owner) {
    final ImmutableList.Builder<CarDto> builder = ImmutableList.builder();
    for (final Car car : this.repo.findCarsByOwner(owner)) {
      builder.add(this.mapper.map(car, CarDto.class));
    }
    return builder.build();
  }

  @GetMapping(value = "cars/{number}", produces = "application/json")
  public ResponseEntity<CarDto> findCar(
      @PathVariable("number") @Validated @NotNull final String number) {
    return this.repo.findCarsByNumber(number).stream()
        .findFirst()
        .map(value -> ResponseEntity.ok(this.mapper.map(value, CarDto.class)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping(value = "cars", consumes = "application/json", produces = "application/json")
  public ResponseEntity<CarDto> addCar(@RequestBody @Validated @NotNull final CarDto dto) {
    final ResponseEntity<CarDto> response;
    if (this.repo.findCarsByNumber(dto.getNumber()).isEmpty()) {
      final Car car = this.mapper.map(dto, Car.class);
      final Car updated = this.repo.save(car);
      final CarDto result = this.mapper.map(updated, CarDto.class);
      response = ResponseEntity.status(HttpStatus.CREATED).body(result);
      try {
        this.loggingService.addLog(
            LogEntry.builder()
                .service("gibdd")
                .created(new Date())
                .message(String.format("Added new car: %s", updated.toString()))
                .build());
      } catch (final FeignException exception) {
        log.error("Failed to sent logs", exception);
      }
    } else {
      response = ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
    }
    return response;
  }

  @PostMapping(value = "cars", consumes = "application/json", produces = "application/json")
  public ResponseEntity<CarDto> updateCar(@RequestBody @Validated final CarDto dto) {
    final ResponseEntity<CarDto> response;
    if (!this.repo.findCarsByNumber(dto.getNumber()).isEmpty()) {
      final Car updates = this.mapper.map(dto, Car.class);
      final Car updated = this.repo.save(updates);
      final CarDto result = this.mapper.map(updated, CarDto.class);
      response = ResponseEntity.status(HttpStatus.OK).body(result);
      try {
        this.loggingService.addLog(
            LogEntry.builder()
                .service("gibdd")
                .created(new Date())
                .message(String.format("Updated car: %s", updated.toString()))
                .build());
      } catch (FeignException exception) {
        log.error("Failed to sent logs", exception);
      }
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }
}
