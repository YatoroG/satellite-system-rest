package sys.kafka.inbox;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import sys.kafka.SatelliteEvent;

@UtilityClass
public class InboxUtils {
    public InboxEvent createInboxEvent(SatelliteEvent event) {
        return InboxEvent.builder()
                .eventId(generateStableEventId(event))
                .aggregateId(event.satelliteId())
                .eventType(event.eventType().name())
                .processedAt(LocalDateTime.now())
                .build();
    }

    public UUID generateStableEventId(SatelliteEvent event) {
        String key = event.satelliteId() + ":" +
                event.eventType() + ":" +
                event.timestamp();
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }
}
