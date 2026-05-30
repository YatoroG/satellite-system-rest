package sys.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sys.services.SatelliteIdRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class SatelliteEventListener {
    private static final String TOPIC = "satellite-events";
    private final SatelliteIdRepository satelliteIdRepository;

    @KafkaListener(topics = TOPIC, groupId = "telemetry-service-group")
    public void handleSatellite(ConsumerRecord<String, SatelliteEvent> record) {
        try {
            SatelliteEvent event = record.value();
            log.info("Получено событие: type = {}, satelliteId = {}, name = {}, offset = {}",
                    event.eventType(), event.satelliteId(), event.satelliteName(), record.offset());
            switch (event.eventType()) {
                case CREATED -> satelliteIdRepository.add(event.satelliteId());
                case DELETED -> satelliteIdRepository.remove(event.satelliteId());
            }
        } catch (Exception e) {
            log.error("Ошибка обработки события (offset = {}): {}", record.offset(), e.getMessage(), e);
        }
    }
}
