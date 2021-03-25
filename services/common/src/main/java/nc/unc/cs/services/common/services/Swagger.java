package nc.unc.cs.services.common.services;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
        .packagesToScan("nc.unc.cs.services")
        .packagesToExclude("nc.unc.cs.services.common.clients")
        .build();
  }
}
