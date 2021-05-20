package nc.unc.cs.gateway;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  private boolean enabled = false;

  @Autowired
  public PingService(final EurekaClient client, final RestTemplate rest) {
    this.client = client;
    this.rest = rest;
  }

  public void setEnabled(boolean value) {
    this.enabled = value;
  }

  @Scheduled(cron = "0/10 * * * * *")
  public void ping() {
    if (this.enabled) {
      this.pingOnce();
    }
  }

  public List<String> pingOnce() {
    List<String> response = new ArrayList<>();
    for (Application application : this.client.getApplications().getRegisteredApplications()) {
      for (InstanceInfo instance : application.getInstances()) {
        String result = "";
        try {
          final ResponseEntity<String> resp =
              this.rest.getForEntity(instance.getHealthCheckUrl(), String.class);
          result =
              String.format(
                  "%s/%s responded with %s code",
                  instance.getId(), instance.getHostName(), resp.getStatusCode().value());
          log.info(result);
        } catch (RestClientException exception) {
          result =
              String.format(
                  "%s/%s failed with %s message",
                  instance.getId(), instance.getHostName(), exception.getMessage());
          log.error(result);
        }
        response.add(result);
      }
    }
    return response;
  }
}
