package sys.domains;

import org.junit.jupiter.api.*;
import sys.domains.satellites.*;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты функциональности группировки (SatelliteConstellation)")
public class SatelliteConstellationUnitTest {
    private static final String CONSTELLATION_NAME = "testConstellation";
    private static final String SATELLITE_NAME_1 = "testSatellite1";
    private static final String SATELLITE_NAME_2 = "testSatellite2";

    private SatelliteConstellation constellation;

    static class TestSatellite extends Satellite {
        private boolean missionExecuted = false;

        public TestSatellite(String name, double batteryLevel) {
            super(name, batteryLevel);
        }

        @Override
        public void performMission() {
            this.missionExecuted = true;
        }

        public boolean isMissionExecuted() {
            return missionExecuted;
        }
    }

    @BeforeEach
    void setUp() {
        constellation = new SatelliteConstellation(CONSTELLATION_NAME);
    }

    @Nested
    class PositiveTests {
        @Test
        @DisplayName("Создание группировки и проверка имени")
        void testConstellationCreation() {
            assertEquals(CONSTELLATION_NAME, constellation.getConstellationName());
            assertTrue(constellation.getSatellites().isEmpty());
        }

        @Test
        @DisplayName("Добавление спутников в группировку")
        void testAddSatellites() {
            TestSatellite sat1 = new TestSatellite(SATELLITE_NAME_1, 0.8);
            TestSatellite sat2 = new TestSatellite(SATELLITE_NAME_2, 0.9);
            constellation.addSatellite(sat1);
            constellation.addSatellite(sat2);
            assertEquals(2, constellation.getSatellites().size());
            assertTrue(constellation.getSatellites().contains(sat1));
        }

        @Test
        @DisplayName("Получение спутника из группировки по имени")
        void testGetSatelliteByName() {
            TestSatellite sat1 = new TestSatellite(SATELLITE_NAME_1, 0.8);
            constellation.addSatellite(sat1);
            Satellite found = constellation.getSatelliteFromConstellation(SATELLITE_NAME_1);
            assertNotNull(found);
            assertEquals(SATELLITE_NAME_1, found.getName());
        }

        @Test
        @DisplayName("Удаление спутника из группировки")
        void testDeleteSatellite() {
            TestSatellite sat1 = new TestSatellite(SATELLITE_NAME_1, 0.8);
            constellation.addSatellite(sat1);
            assertEquals(1, constellation.getSatellites().size());
            constellation.deleteSatellite(sat1);
            assertTrue(constellation.getSatellites().isEmpty());
        }

        @Test
        @DisplayName("Выполнение всех миссий")
        void testExecuteAllMissions() {
            TestSatellite sat1 = new TestSatellite(SATELLITE_NAME_1, 0.8);
            TestSatellite sat2 = new TestSatellite(SATELLITE_NAME_2, 0.8);
            constellation.addSatellite(sat1);
            constellation.addSatellite(sat2);
            constellation.executeAllMissions();
            assertTrue(sat1.isMissionExecuted());
            assertTrue(sat2.isMissionExecuted());
        }
    }

    @Nested
    @DisplayName("Негативные сценарии")
    class NegativeTests {
        @Test
        @DisplayName("Добавление пустого объекта")
        void testAddNullSatellite() {
            constellation.addSatellite(null);
            assertTrue(constellation.getSatellites().isEmpty());
        }

        @Test
        @DisplayName("Добавление дубликата одного и того же объекта")
        void testAddDuplicateSatellite() {
            TestSatellite sat1 = new TestSatellite(SATELLITE_NAME_1, 0.8);
            constellation.addSatellite(sat1);
            constellation.addSatellite(sat1);
            assertEquals(1, constellation.getSatellites().size());
        }

        @Test
        @DisplayName("Поиск несуществующего спутника")
        void testGetNonExistentSatellite() {
            constellation.addSatellite(new TestSatellite(SATELLITE_NAME_1, 0.8));
            Satellite found = constellation.getSatelliteFromConstellation("nonExistentSatellite");
            assertNull(found);
        }
    }
}
