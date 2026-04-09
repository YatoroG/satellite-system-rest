package sys.configurations;

public record MissionPropertyRequest(String targetType, String constellationName,
                                     String satelliteName, String cron) {
}
