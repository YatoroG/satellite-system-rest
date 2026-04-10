package sys.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import sys.configurations.SpaceOperationClient;
import sys.domains.requests.MissionRequest;
import sys.properties.SpaceCenterServiceProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfiguredMissionScheduler {
    private final SpaceCenterServiceProperties properties;
    private final TaskScheduler taskScheduler;
    private final SpaceOperationClient client;

    @PostConstruct
    public void init() {
        for (SpaceCenterServiceProperties.ConfiguredMission mission : properties.missions()) {
            MissionRequest req = new MissionRequest(
                    mission.getTargetType(),
                    mission.getConstellationName(),
                    mission.getSatelliteName()
            );
            taskScheduler.schedule(
                    () -> {
                        try {
                            client.executeMission(req);
                            log.info("Миссия выполнена: {}", req);
                        } catch (Exception e) {
                            log.error("Ошибка в запланированной миссии: {}", e.getMessage());
                        }
                    },
                    new CronTrigger(mission.getCron())
            );
            log.info("Запланирована миссия {} с cron '{}'", req, mission.getCron());
        }
    }
}
