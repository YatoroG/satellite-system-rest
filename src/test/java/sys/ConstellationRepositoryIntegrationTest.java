package sys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sys.repository.ConstellationRepository;
import sys.service.SpaceOperationCenterService;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Интеграционные тесты функциональности репозитория")
public class ConstellationRepositoryIntegrationTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";
    private static final String SATELLITE_1 = "testSatellite1";
    private static final String SATELLITE_2 = "testSatellite2";

    @Autowired
    private ConstellationRepository repository;

    @Autowired
    private SpaceOperationCenterService service;

    @BeforeEach
    void setUp() {
        repository = new ConstellationRepository();
        service = new SpaceOperationCenterService(repository);
    }

    @Test
    @DisplayName("Тестирование репозитория")
    void testFullConstellationLifecycle() {
        SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

        repository.addConstellation(constellation1);
        repository.addConstellation(constellation2);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
        assertTrue(repository.containsConstellation(CONSTELLATION_2));

        service.addSatelliteToConstellation(CONSTELLATION_1,
                new CommunicationSatellite(SATELLITE_1, 0.8, 500));
        service.addSatelliteToConstellation(CONSTELLATION_1,
                new ImagingSatellite(SATELLITE_2, 0.7, 50));

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
