package sys.domains.requests;

public record MissionRequest(MissionTargetType targetType, String constellationName, String satelliteName) {
}
