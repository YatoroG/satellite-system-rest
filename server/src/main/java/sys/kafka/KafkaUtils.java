package sys.kafka;

import java.time.Instant;
import lombok.experimental.UtilityClass;
import sys.domains.satellites.Satellite;

@UtilityClass
public class KafkaUtils {
    public static SatelliteEvent createEvent(Satellite satellite, SatelliteEvent.EventType eventType) {
        return new SatelliteEvent(
                satellite.getId(), satellite.getName(), eventType, Instant.now()
        );
    }
}
