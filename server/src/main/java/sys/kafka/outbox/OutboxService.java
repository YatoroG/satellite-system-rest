package sys.kafka.outbox;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.kafka.KafkaService;
import sys.kafka.SatelliteEvent;

@Slf4j
@RequiredArgsConstructor
@Service
public class OutboxService {
    private final KafkaService kafkaService;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    private static final String SATELLITE_EVENTS_TOPIC = "satellite-events";
    private static final int BATCH_SIZE = 50;

    public void publishToOutbox(Long satId, SatelliteEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateId(satId)
                    .eventType(event.eventType().name())
                    .payload(payload)
                    .createdAt(LocalDateTime.now())
                    .status(OutboxEvent.OutboxStatus.PENDING)
                    .build();
            outboxEventRepository.save(outboxEvent);
        } catch (Exception e) {
            log.error("Ошибка сериализации outbox-события", e);
            throw new RuntimeException("Failed to serialize outbox event", e);
        }
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> pending = outboxEventRepository.findByStatusOrderByCreatedAtAsc(
                OutboxEvent.OutboxStatus.PENDING, PageRequest.of(0, BATCH_SIZE));

        if (pending.isEmpty()) {
            return;
        }

        for (OutboxEvent event : pending) {
            try {
                SatelliteEvent satelliteEvent = objectMapper.readValue(event.getPayload(), SatelliteEvent.class);
                kafkaService.sentToKafkaSatellite(
                        SATELLITE_EVENTS_TOPIC,
                        satelliteEvent
                );

                outboxEventRepository.updateStatus(event.getId(), OutboxEvent.OutboxStatus.SENT);
                log.info("Outbox event {} sent to Kafka", event.getId());
            } catch (Exception e) {
                log.error("Failed to sent outbox event {}: {}", event.getId(), e.getMessage());
                outboxEventRepository.updateStatus(event.getId(), OutboxEvent.OutboxStatus.FAILED);
            }
        }
    }
}
