package nc.unc.cs.services.common.services;

import feign.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new Builder().connectTimeout(2, TimeUnit.SECONDS).build();
    }

    @Bean
    @Autowired
    public OkHttpClient client(okhttp3.OkHttpClient okHttpClient) {
        return new OkHttpClient(okHttpClient);
    }
}
