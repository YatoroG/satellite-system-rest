package sys.kafka.inbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InboxService {
    private final InboxEventRepository inboxEventRepository;

    @Transactional(readOnly = true)
    public boolean existsByEventId(UUID eventId) {
        return inboxEventRepository.existsByEventId(eventId);
    }

    @Transactional
    public void saveToInbox(InboxEvent inboxEvent) {
        inboxEventRepository.save(inboxEvent);
        log.info("Событие с ID {} зафиксировано", inboxEvent.getEventId());
    }
}
