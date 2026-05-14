package sys.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.repository.SatelliteRepository;
import sys.utils.SpaceOperationException;

@Service
@RequiredArgsConstructor
@Transactional
public class SatelliteService {
    private final SatelliteRepository satelliteRepository;
    private final SatelliteFactoryService satelliteFactoryService;

    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        Satellite satellite = satelliteFactoryService.createSatellite(param);
        return satelliteRepository.save(satellite);
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
        if (!satelliteRepository.existsById(id)) {
            throw new RuntimeException("Спутник с id = " + id + " не найден");
        }
        satelliteRepository.deleteById(id);
    }
}
