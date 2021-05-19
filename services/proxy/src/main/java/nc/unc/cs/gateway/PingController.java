package nc.unc.cs.gateway;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {

  private final PingService pingService;

  @Autowired
  public PingController(PingService pingService) {
    this.pingService = pingService;
  }

  @GetMapping(value = "/enable", produces = "application/json")
  public ResponseEntity<Void> enablePing() {
    this.pingService.setEnabled(true);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(value = "/disable", produces = "application/json")
  public ResponseEntity<Void> disablePing() {
    this.pingService.setEnabled(false);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(value = "/run", produces = "application/json")
  public ResponseEntity<List<String>> runPing() {
    final List<String> response = this.pingService.pingOnce();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
