package sys.domains.requests.questions;

import java.util.Set;
import sys.domains.requests.MissionTargetType;
import sys.utils.SatelliteType;

public record MissionRequestWithType(MissionTargetType targetType,
                                     String constellationName,
                                     String satelliteName,
                                     Set<SatelliteType> targetTypes) {
}
