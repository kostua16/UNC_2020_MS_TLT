package nc.unc.cs.services.common.clients.passport;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PASSPORT", path = "passport", url = "${app.passport-url}")
public interface PassportService {
  @PostMapping(value = "/registerDomestic", produces = "application/json")
  ResponseEntity<Domestic> registerDomesticPassport(@RequestBody final Citizen citizen);
}
