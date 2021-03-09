package nc.unc.cs.services.common.actuator;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

@Endpoint(id = "eureka")
@Component
@Slf4j
public class DiscoveryServices {

  private final OkHttpClient httpClient;

  private final EurekaClient client;

  private final List<Integer> correctCodes = Arrays.asList(200, 401, 403);

  @Autowired
  public DiscoveryServices(final EurekaClient client,
                           final OkHttpClient httpClient) {
    this.client = client;
    this.httpClient = httpClient;
  }

  @ReadOperation
  public List<Application> listServices() {
    return this.client.getApplications().getRegisteredApplications();
  }

  @ReadOperation
  public List<String> serviceInfo(@Selector String name) throws IOException {
    List<String> result = new ArrayList<>();
    final Optional<Application> service =
        this.client.getApplications()
            .getRegisteredApplications()
            .stream()
            .filter(app -> name.equals(app.getName()))
            .findFirst();

    if (service.isPresent()) {
      String current = "";
      final Application application = service.get();
      for (InstanceInfo instance : application.getInstances()) {
        log.error(String.format("Testing %s - %s...", application.getName(),
                                instance.getId()));
        try {
          final Call call = this.httpClient.newCall(
              new Request.Builder()
                  .get()
                  .url(new URL(instance.getHealthCheckUrl()))
                  .build());
          final Response response = call.execute();
          current = String.format("[%s = %s, Code: %d, Success: %b]",
                                  instance.getId(), instance.getHostName(),
                                  response.code(), response.isSuccessful());

        } catch (IOException exception) {
          log.error("Test failed", exception);
          String.format("[%s = %s, Fail: %s]", instance.getId(),
                        instance.getHostName(), exception.getMessage());
        }
        result.add(current);
        log.error(current);
      }
    }
    return result;
  }
}
