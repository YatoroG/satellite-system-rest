package sys.configurations;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.space-center-service")
public record SpaceCenterConfigReader(String url, List<MissionPropertyRequest> missions) {
}
