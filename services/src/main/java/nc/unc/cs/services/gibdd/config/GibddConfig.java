package nc.unc.cs.services.gibdd.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("gibdd")
@EnableDiscoveryClient
public class GibddConfig {

}
