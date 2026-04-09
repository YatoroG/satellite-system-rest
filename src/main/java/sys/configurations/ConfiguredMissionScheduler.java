package sys.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class ConfiguredMissionScheduler {
    private final SpaceCenterConfigReader config;
    private final TaskScheduler taskScheduler;
    private final SpaceOperationClient client;

    public ConfiguredMissionScheduler(SpaceCenterConfigReader config,
                                      TaskScheduler taskScheduler,
                                      SpaceOperationClient apiClient) {
        this.config = config;
        this.taskScheduler = taskScheduler;
        this.client = apiClient;
    }

    @PostConstruct
    public void scheduleMissions() {
        if (config.missions() == null) return;

        for (MissionPropertyRequest mission : config.missions()) {
            taskScheduler.schedule(
                    () -> runTask(mission),
                    new CronTrigger(mission.cron())
            );
            System.out.println("Миссия запланирована: " + mission.constellationName() + " (" + mission.cron() + ")");
        }
    }

    private void runTask(MissionPropertyRequest prop) {
        System.out.println("Запуск плановой миссии для: " + prop.constellationName());
        try {
            client.executeMission(prop);
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении миссии: " + e.getMessage());
        }
    }
}
