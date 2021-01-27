package nc.unc.cs.services.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "nc.unc.cs.services")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "nc.unc.cs.services")
public class PassportApp {
    public static void main(String[] args) {
        SpringApplication.run(PassportApp.class);
    }
}
