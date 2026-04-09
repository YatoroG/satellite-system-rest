package sys.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteConstellation;
import sys.repository.ConstellationRepository;

@Service
public class ConstellationService {
    private final ConstellationRepository repository;

    public ConstellationService(ConstellationRepository repository) {
        this.repository = repository;
    }

    public ConstellationRepository getRepository() {
        return repository;
    }

    public void createAndSaveConstellation(String name) {
        SatelliteConstellation constellation = new SatelliteConstellation(name);
        repository.addConstellation(constellation);
    }

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        SatelliteConstellation constellation = repository.getConstellation(constellationName);
        constellation.addSatellite(satellite);
        System.out.println("Добавлен спутник '" + satellite.getName() + "' в группировку '" + constellationName + "'");
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = repository.getConstellation(constellationName);
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ ГРУППИРОВКИ '" + constellationName + "'");
        System.out.println("============================================================");
        constellation.executeAllMissions();
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = repository.getConstellation(constellationName);
        System.out.println("АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ '" + constellationName + "'");
        System.out.println("============================================================");
        for (Satellite satellite : constellation.getSatellites()) {
            satellite.activate();
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = repository.getConstellation(constellationName);
        System.out.println("СТАТУС ГРУППИРОВКИ '" + constellationName + "'");
        System.out.println("============================================================");
        System.out.println("Количество спутников в группировке: " + constellation.getSatellites().size());
        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite.getState());
        }
    }

    public SatelliteConstellation getConstellation(String name) {
        return repository.getConstellation(name);
    }

    public Map<String, SatelliteConstellation> getAllConstellations() {
        return repository.getAllConstellations();
    }

    public void deleteSatelliteFromConstellation(String constellationName, String satelliteName) {
        SatelliteConstellation constellation = repository.getConstellation(constellationName);
        Satellite satellite = constellation.getSatelliteFromConstellation(satelliteName);
        if (satellite == null) {
            throw new RuntimeException("Спутник " + satelliteName + " не найден в группировке " + constellationName);
        }
        constellation.deleteSatellite(satellite);
        System.out.println("Спутник '" + satelliteName + "' удален из группировки '" + constellationName + "'");
    }
}
