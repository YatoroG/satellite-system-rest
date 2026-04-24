package sys.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import sys.properties.SpaceCenterServiceProperties;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SpaceCenterServiceProperties.class)
public class RestClientConfig {
    private final SpaceCenterServiceProperties properties;

    @Bean
    public RestClient spaceOperationRestClient() {
        return RestClient.builder()
                .baseUrl(properties.url())
                .build();
    }
}
