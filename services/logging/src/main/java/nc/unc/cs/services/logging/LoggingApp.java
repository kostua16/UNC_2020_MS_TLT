package nc.unc.cs.services.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoggingApp {
    public static void main(String[] args) {
        SpringApplication.run(LoggingApp.class, args);
    }
}
