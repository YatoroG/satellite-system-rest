package sys.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.kafka.KafkaService;
import sys.kafka.KafkaUtils;
import sys.kafka.SatelliteEvent;
import sys.repository.SatelliteRepository;
import sys.utils.SpaceOperationException;

@Service
@RequiredArgsConstructor
@Transactional
public class SatelliteService {
    private static final String SATELLITE_EVENTS_TOPIC = "satellite-events";

    private final SatelliteRepository satelliteRepository;
    private final SatelliteFactoryService satelliteFactoryService;
    private final KafkaService kafkaService;

    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        Satellite satellite = satelliteFactoryService.createSatellite(param);
        Satellite saved = satelliteRepository.save(satellite);
        kafkaService.sentToKafkaSatellite(
                SATELLITE_EVENTS_TOPIC,
                KafkaUtils.createEvent(saved, SatelliteEvent.EventType.CREATED)
        );
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Satellite> getAllSatellites() {
        return satelliteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Satellite> getSatelliteById(Long id) {
        return satelliteRepository.findById(id);
    }

    public Satellite updateSatellite(Long id, String newName) {
        Satellite satellite = satelliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Спутник не найден"));
        if (newName != null && !newName.isBlank()) {
            satellite.setName(newName);
        }
        return satelliteRepository.save(satellite);
    }

    public void deleteSatellite(Long id) {
        Satellite satellite = satelliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Спутник с id = " + id + " не найден"));

        satelliteRepository.deleteById(id);

        kafkaService.sentToKafkaSatellite(
                SATELLITE_EVENTS_TOPIC,
                KafkaUtils.createEvent(satellite, SatelliteEvent.EventType.DELETED)
        );
    }
}
