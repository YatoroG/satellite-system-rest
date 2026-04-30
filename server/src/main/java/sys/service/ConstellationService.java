package sys.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteConstellation;
import sys.repository.ConstellationRepository;
import sys.repository.SatelliteRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final SatelliteRepository satelliteRepository;

    public SatelliteConstellation createAndSaveConstellation(String name) {
        SatelliteConstellation constellation = new SatelliteConstellation(name);
        return constellationRepository.save(constellation);
    }

    @Transactional(readOnly = true)
    public SatelliteConstellation getConstellationByName(String name) {
        return constellationRepository.findByConstellationName(name)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена: " + name));
    }

    @Transactional(readOnly = true)
    public Optional<SatelliteConstellation> getConstellationById(Long id) {
        return constellationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationRepository.findAll();
    }

    public void addSatelliteToConstellation(Long constellationId, Long satelliteId) {
        SatelliteConstellation constellation = constellationRepository.findById(constellationId)
                        .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
        Satellite satellite = satelliteRepository.findById(satelliteId)
                        .orElseThrow(() -> new RuntimeException("Спутник не найден"));
        constellation.addSatellite(satellite);
        satellite.setConstellation(constellation);
        satelliteRepository.save(satellite);
        System.out.println("Добавлен спутник '" + satellite.getName() + "' в группировку '" + constellation.getConstellationName() + "'");
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ ГРУППИРОВКИ '" + constellationName + "'");
        System.out.println("============================================================");
        constellation.getSatellites().forEach(Satellite::performMission);
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        System.out.println("АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ '" + constellationName + "'");
        System.out.println("============================================================");
        constellation.getSatellites().forEach(Satellite::activate);
    }

    public void deleteConstellation(Long id) {
        if (!constellationRepository.existsById(id)) {
            throw new RuntimeException("Группировка с id = " + id + " не найдена");
        }
        constellationRepository.deleteById(id);
    }

    public void deleteSatelliteFromConstellation(String constellationName, String satelliteName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        Satellite satellite = constellation.getSatellites().stream()
                .filter(sat -> sat.getName().equals(satelliteName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Спутник не найден"));
        constellation.deleteSatellite(satellite);
        satelliteRepository.delete(satellite);
        System.out.println("Спутник '" + satelliteName + "' удален из группировки '" + constellationName + "'");
    }
}
