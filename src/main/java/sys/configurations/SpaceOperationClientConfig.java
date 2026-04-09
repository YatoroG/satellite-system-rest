package sys.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SpaceOperationClientConfig {
    @Bean
    public RestClient spaceOperationRestClient(SpaceCenterConfigReader config) {
        return RestClient.builder()
                .baseUrl(config.url())
                .build();
    }
}
