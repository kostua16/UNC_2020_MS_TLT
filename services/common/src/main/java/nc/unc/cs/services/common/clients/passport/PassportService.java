package nc.unc.cs.services.common.clients.passport;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PASSPORT", path = "passport", url = "${app.passport-url}")
@ConditionalOnMissingClass("nc.unc.cs.services.passport.controllers.PassportController")
public interface PassportService {
  @PutMapping(value = "domestic/registration/{citizenId}", produces = "application/json")
  ResponseEntity<Long> updateDomesticRegistration(
      @PathVariable("citizenId") final Long citizenId,
      @RequestBody final UpdateRegistrationIdDto updateRegistrationIdDto);
}
