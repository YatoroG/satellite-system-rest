package sys.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sys.domains.requests.AddSatelliteRequest;
import sys.domains.requests.MissionRequest;
import sys.domains.requests.MissionTargetType;
import sys.domains.satellites.CommunicationSatelliteParam;
import sys.domains.satellites.ImagingSatelliteParam;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteConstellation;
import sys.repository.ConstellationRepository;
import sys.utils.SpaceOperationException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Юнит-тесты функциональности сервиса операций")
public class SpaceOperationCenterServiceTest {
    @Autowired
    private SpaceOperationCenterService service;

    @Autowired
    private ConstellationRepository constellationRepository;

    private String uniqueName(String base) {
        return base + "_" + System.currentTimeMillis();
    }

    @Test
    @DisplayName("Добавление спутников в группировку через фасад операций")
    void addSatelliteTest() throws SpaceOperationException {
        String constellationName = uniqueName("AddSatellitesConstellation");
        String commSatName = "CommSat-1";
        String imgSatName = "ImgSat-1";

        var commParam = new CommunicationSatelliteParam(commSatName, 0.9, 500.0);
        var imgParam = new ImagingSatelliteParam(imgSatName, 0.8, 2.5);
        var request = new AddSatelliteRequest(
                constellationName,
                List.of(commParam, imgParam)
        );

        service.addSatellite(request);

        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        assertNotNull(constellation, "Группировка должна существовать");
        assertEquals(2, constellation.getSatellites().size(), "Должно быть 2 спутника");

        List<String> satelliteNames = constellation.getSatellites().stream()
                .map(Satellite::getName)
                .toList();

        assertTrue(satelliteNames.contains(commSatName));
        assertTrue(satelliteNames.contains(imgSatName));
    }

    @Test
    @DisplayName("Выполнение миссий в группировке через фасад операций")
    void executeAllMissionsTest() throws SpaceOperationException {
        String constellationName = uniqueName("MissionConstellation");
        String satName = "Satellite";

        var commParam = new CommunicationSatelliteParam(satName, 0.9, 500.0);
        var addRequest = new AddSatelliteRequest(constellationName, List.of(commParam));
        service.addSatellite(addRequest);

        var missionRequest = new MissionRequest(MissionTargetType.CONSTELLATION, constellationName, null);
        service.executeMission(missionRequest);

        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        Satellite satellite = constellation.getSatellites().get(0);
        assertTrue(satellite.getState().isActive(), "Спутник должен быть активен после миссии");
    }
}
