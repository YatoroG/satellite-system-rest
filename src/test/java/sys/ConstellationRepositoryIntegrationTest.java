package sys;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sys.domains.satellites.*;
import sys.factory.impl.CommunicationSatelliteFactory;
import sys.factory.impl.ImagingSatelliteFactory;
import sys.repository.ConstellationRepository;
import sys.service.ConstellationService;
import sys.utils.SpaceOperationException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Интеграционные тесты функциональности репозитория")
public class ConstellationRepositoryIntegrationTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";
    private static final String SATELLITE_1 = "testSatellite1";
    private static final String SATELLITE_2 = "testSatellite2";
    private static final double BATTERY_LEVEL_1 = 0.8;
    private static final double BATTERY_LEVEL_2 = 0.5;
    private static final double BANDWIDTH = 300.0;
    private static final double RESOLUTION = 15.0;
    @Autowired
    private ConstellationRepository repository;

    @Autowired
    private ConstellationService service;

    @Autowired
    private CommunicationSatelliteFactory commFactory;

    @Autowired
    private ImagingSatelliteFactory imgFactory;

    @Test
    @DisplayName("Тестирование репозитория")
    void testFullConstellationLifecycle() throws SpaceOperationException {
        SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

        repository.addConstellation(constellation1);
        repository.addConstellation(constellation2);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
        assertTrue(repository.containsConstellation(CONSTELLATION_2));

        CommunicationSatelliteParam commParams = new CommunicationSatelliteParam(
                SATELLITE_1,
                BATTERY_LEVEL_1,
                BANDWIDTH
        );

        CommunicationSatellite commSatellite = (CommunicationSatellite) commFactory
                .createSatelliteWithParameter(commParams);

        service.addSatelliteToConstellation(CONSTELLATION_1, commSatellite);

        ImagingSatelliteParam params = new ImagingSatelliteParam(
                SATELLITE_2,
                BATTERY_LEVEL_2,
                RESOLUTION
        );

        ImagingSatellite imgSatellite = (ImagingSatellite) imgFactory
                .createSatelliteWithParameter(params);

        service.addSatelliteToConstellation(CONSTELLATION_1, imgSatellite);

        assertEquals(2, repository.getConstellation(CONSTELLATION_1).getSatellites().size());

        repository.getConstellation(CONSTELLATION_1).getSatellites()
                .forEach(satellite -> assertFalse(satellite.getState().isActive()));

        service.activateAllSatellites(CONSTELLATION_1);

        repository.getConstellation(CONSTELLATION_1).getSatellites()
                .forEach(satellite -> assertTrue(satellite.getState().isActive()));

        constellation1.executeAllMissions();

        assertTrue(repository.removeConstellation(CONSTELLATION_1));
        assertFalse(repository.containsConstellation(CONSTELLATION_1));
        assertEquals(1, repository.getAllConstellations().size());

        assertTrue(repository.removeConstellation(CONSTELLATION_2));
        assertFalse(repository.containsConstellation(CONSTELLATION_2));
        assertTrue(repository.getAllConstellations().isEmpty());
    }
}
