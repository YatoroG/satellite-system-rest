package sys;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sys.repository.ConstellationRepository;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты функциональности репозитория")
public class ConstellationRepositoryUnitTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";

    private ConstellationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ConstellationRepository();
    }

    @Nested
    class PositiveTests {
        @Test
        @DisplayName("Добавление группировки")
        void testAddConstellation() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);

            repository.addConstellation(constellation1);

            assertTrue(repository.containsConstellation(CONSTELLATION_1));
            assertEquals(1, repository.getAllConstellations().size());
        }

        @Test
        @DisplayName("Добавление нескольких группировок")
        void testAddMultipleConstellations() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
            SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

            repository.addConstellation(constellation1);
            repository.addConstellation(constellation2);

            assertTrue(repository.containsConstellation(CONSTELLATION_1));
            assertTrue(repository.containsConstellation(CONSTELLATION_2));
            assertEquals(2, repository.getAllConstellations().size());
        }

        @Test
        @DisplayName("Получение группировки")
        void testGetConstellation() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);

            repository.addConstellation(constellation1);
            assertTrue(repository.containsConstellation(CONSTELLATION_1));

            assertEquals(constellation1, repository.getConstellation(CONSTELLATION_1));
        }

        @Test
        @DisplayName("Получение всех группировок")
        void testGetAllConstellation() {
            Map<String, SatelliteConstellation> satellites = new HashMap<>();

            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
            SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

            satellites.put(constellation1.getConstellationName(), constellation1);
            satellites.put(constellation2.getConstellationName(), constellation2);

            repository.addConstellation(constellation1);
            repository.addConstellation(constellation2);

            assertTrue(repository.containsConstellation(CONSTELLATION_1));
            assertTrue(repository.containsConstellation(CONSTELLATION_2));

            assertEquals(satellites, repository.getAllConstellations());
        }

        @Test
        @DisplayName("Удаление группировки")
        void testDeleteConstellation() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);

            repository.addConstellation(constellation1);
            assertTrue(repository.containsConstellation(CONSTELLATION_1));

            assertTrue(repository.removeConstellation(CONSTELLATION_1));
            assertFalse(repository.containsConstellation(CONSTELLATION_1));
            assertTrue(repository.getAllConstellations().isEmpty());
        }
    }

    @Nested
    class NegativeTests {
        @Test
        @DisplayName("Удаление несуществующей группировки")
        void testDeleteNullConstellation() {
            assertTrue(repository.getAllConstellations().isEmpty());
            assertFalse(repository.removeConstellation(CONSTELLATION_1));
        }

        @Test
        @DisplayName("Получение несуществующей группировки")
        void testGetNullConstellation() {
            assertTrue(repository.getAllConstellations().isEmpty());
            assertThrows(RuntimeException.class, () -> repository.getConstellation(CONSTELLATION_1) );
        }
    }
}
