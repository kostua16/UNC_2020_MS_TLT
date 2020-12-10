package nc.unc.cs.services.logging.services;

import java.util.List;
import nc.unc.cs.services.logging.entities.LogEntry;
import nc.unc.cs.services.logging.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logs")
@RefreshScope
public class LogsService {

    private final LogsRepository repository;

    @Autowired
    public LogsService(final LogsRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<LogEntry> viewLastLogs() {
        return this.repository.findAll(
            PageRequest.of(1,15, Sort.by(Sort.Direction.DESC, "created"))
        ).getContent();
    }

    @GetMapping(path = "/{service}", produces = "application/json")
    public List<LogEntry> viewLogs(@PathVariable final String service) {
        return this.repository.findLogEntriesByService(
            service,
            PageRequest.of(1,15, Sort.by(Sort.Direction.DESC, "created"))
        ).getContent();
    }

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public LogEntry addLog(@RequestBody final LogEntry log) {
        return this.repository.save(log);
    }
}
