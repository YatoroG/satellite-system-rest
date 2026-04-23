package sys.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sys.domains.requests.AddSatelliteRequest;
import sys.domains.requests.MissionRequest;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.utils.SpaceOperationException;

@RequiredArgsConstructor
@Service
public class SpaceOperationCenterService {
    private final ConstellationService constellationService;

    private final SatelliteService satelliteService;

    public void addSatellite(AddSatelliteRequest request) throws SpaceOperationException {
        try {
            constellationService.showConstellationStatus(request.constellationName());
        } catch (Exception e) {
            constellationService.createAndSaveConstellation(request.constellationName());
        }

        for (SatelliteParam param : request.satelliteParams()) {
            Satellite satellite = satelliteService.createSatellite(param);
            constellationService.addSatelliteToConstellation(request.constellationName(), satellite);
        }
    }

    public void executeMission(MissionRequest request) {
        switch (request.targetType()) {
            case SINGLE -> {
                var constellation = constellationService.getConstellation(request.constellationName());
                var satellite = constellation.getSatellites().stream()
                        .filter(s -> s.getName().equals(request.satelliteName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден: " + request.satelliteName()));
                satellite.activate();
                satellite.performMission();
            }
            case CONSTELLATION -> {
                constellationService.activateAllSatellites(request.constellationName());
                constellationService.executeConstellationMission(request.constellationName());
            }
        }
    }

    public String getSystemOverview() {
        var allConstellations = constellationService.getAllConstellations();
        StringBuilder sb = new StringBuilder("=== СИСТЕМНАЯ СВОДКА ===\n");

        sb.append("Всего группировок: ")
                .append(allConstellations.size())
                .append("\n");

        allConstellations.values().forEach(cons -> {
            sb.append("  Группировка '")
                    .append(cons.getConstellationName())
                    .append("': спутников ")
                    .append(cons.getSatellites().size())
                    .append("\n");

            cons.getSatellites().forEach(satellite ->
                    sb.append("   - ")
                            .append(satellite.getName())
                            .append(" [")
                            .append(satellite.getState().isActive() ? "Активен" : "Неактивен")
                            .append("], заряд: ")
                            .append((int) (satellite.getEnergy().getBatteryLevel() * 100)).append("\n")
            );
        });

        return sb.toString();
    }

    public void deleteSatellite(String constellationName, String satelliteName) {
        constellationService.deleteSatelliteFromConstellation(constellationName, satelliteName);
    }
}

