package nc.unc.cs.services.communal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "nc.unc.cs.services")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "nc.unc.cs.services")
public class CommunalApplication {
  public static void main(String[] args) {
    SpringApplication.run(CommunalApplication.class);
  }
}
