package sys.properties;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import sys.domains.requests.MissionTargetType;

@ConfigurationProperties("app.space-center-service")
public record SpaceCenterServiceProperties(String url, List<ConfiguredMission> missions) {
    @Data
    public static class ConfiguredMission {
        private MissionTargetType targetType;
        private String constellationName;
        private String satelliteName;
        private String cron;
    }
}

