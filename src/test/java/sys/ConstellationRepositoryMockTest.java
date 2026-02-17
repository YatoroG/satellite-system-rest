package sys;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sys.repository.ConstellationRepository;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Мок-тесты функциональности репозитория")
public class ConstellationRepositoryMockTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";

    @Mock
    private ConstellationRepository repository;

    @Nested
    class PositiveTests {
        @Test
        @DisplayName("Добавление группировки")
        void testAddConstellationMock() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);

            repository.addConstellation(constellation1);

            verify(repository).addConstellation(constellation1);

            when(repository.containsConstellation(CONSTELLATION_1)).thenReturn(true);
            assertTrue(repository.containsConstellation(CONSTELLATION_1));
        }

        @Test
        @DisplayName("Добавление нескольких группировок")
        void testAddMultipleConstellationsMock() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
            SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

            repository.addConstellation(constellation1);
            repository.addConstellation(constellation2);

            verify(repository).addConstellation(constellation1);
            verify(repository).addConstellation(constellation2);

            when(repository.containsConstellation(CONSTELLATION_1)).thenReturn(true);
            assertTrue(repository.containsConstellation(CONSTELLATION_1));
        }

        @Test
        @DisplayName("Получение группировки")
        void testGetConstellationMock() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
            when(repository.getConstellation(CONSTELLATION_1)).thenReturn(constellation1);

            SatelliteConstellation result = repository.getConstellation(CONSTELLATION_1);

            assertEquals(constellation1, result);
            verify(repository, times(1)).getConstellation(CONSTELLATION_1);
        }

        @Test
        @DisplayName("Получение всех группировок")
        void testGetAllConstellationMock() {
            SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
            SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

            Map<String, SatelliteConstellation> satellites = new HashMap<>();
            satellites.put(constellation1.getConstellationName(), constellation1);
            satellites.put(constellation2.getConstellationName(), constellation2);

            when(repository.getAllConstellations()).thenReturn(satellites);

            Map<String, SatelliteConstellation> result = repository.getAllConstellations();

            assertEquals(result, satellites);
            verify(repository, times(1)).getAllConstellations();
        }

        @Test
        @DisplayName("Удаление группировки")
        void testDeleteConstellationMock() {
            when(repository.removeConstellation(CONSTELLATION_1)).thenReturn(true);

            boolean result = repository.removeConstellation(CONSTELLATION_1);

            assertTrue(result);
            verify(repository).removeConstellation(CONSTELLATION_1);
        }
    }

    @Nested
    class NegativeTests {
        @Test
        @DisplayName("Удаление несуществующей группировки")
        void testDeleteNullConstellationMock() {
            when(repository.removeConstellation(CONSTELLATION_1)).thenReturn(false);

            boolean result = repository.removeConstellation(CONSTELLATION_1);

            assertFalse(result);
            verify(repository).removeConstellation(CONSTELLATION_1);
        }

        @Test
        @DisplayName("Получение несуществующей группировки")
        void testGetNullConstellationMock() {
            when(repository.getConstellation(CONSTELLATION_1)).thenThrow(RuntimeException.class);

            assertThrows(RuntimeException.class, () -> repository.getConstellation(CONSTELLATION_1));

            verify(repository).getConstellation(CONSTELLATION_1);
        }
    }
}
