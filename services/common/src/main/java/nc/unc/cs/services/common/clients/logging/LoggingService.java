package nc.unc.cs.services.common.clients.logging;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LOGGER", path = "logs", url = "${app.logging-url}")
@ConditionalOnMissingClass("nc.unc.cs.services.logging.services.LogsService")
public interface LoggingService {
  @GetMapping(path = "/", produces = "application/json")
  public List<LogEntry> viewLastLogs();

  @GetMapping(path = "/{service}", produces = "application/json")
  public List<LogEntry> viewLogs(@PathVariable final String service);

  @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
  public LogEntry addLog(@RequestBody final LogEntry log);
}
