package nc.unc.cs.services.common.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class Swagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                   .select()
                   .apis(
                       RequestHandlerSelectors.basePackage("nc.unc.cs.services")
                           .and(
                               RequestHandlerSelectors.basePackage(
                                   "nc.unc.cs.services.common.clients")
                                   .negate()))
                   .paths(PathSelectors.any())
                   .build();
    }
}
