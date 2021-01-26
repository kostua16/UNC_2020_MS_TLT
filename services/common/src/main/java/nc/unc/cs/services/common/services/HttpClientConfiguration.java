package nc.unc.cs.services.common.services;

import java.util.concurrent.TimeUnit;
import feign.okhttp.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public OkHttpClient client() {
        okhttp3.OkHttpClient client =
            new Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        return new OkHttpClient(client);
    }
}
