package nc.unc.cs.gateway;

import java.util.Optional;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PingService {

  private final EurekaClient client;

  private final RestTemplate rest;

  @Autowired
  public PingService(final EurekaClient client, final RestTemplate rest) {
    this.client = client;
    this.rest = rest;
  }

  @Scheduled(cron = "0/10 * * * * *")
  public void serviceInfo(@Selector String name) {
    final Optional<Application> service =
        this.client.getApplications().getRegisteredApplications().stream()
            .filter(app -> name.equals(app.getName()))
            .findFirst();

    if (service.isPresent()) {
      final Application application = service.get();
      for (InstanceInfo instance : application.getInstances()) {
        try {
          final ResponseEntity<String> resp = this.rest.getForEntity(instance.getHealthCheckUrl(), String.class);
          log.info("{}/{} responded with {} code", instance.getId(), instance.getHostName(),
              resp.getStatusCode().value());
        } catch (RestClientException exception) {
          log.error("{}/{} failed with {} message", instance.getId(), instance.getHostName(), exception.getMessage());
        }
      }
    }
  }
}
