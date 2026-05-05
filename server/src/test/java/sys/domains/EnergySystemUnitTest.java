package sys.domains;

import org.junit.jupiter.api.*;
import sys.domains.satellites.EnergySystem;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты функциональности сущности EnergySystem")
public class EnergySystemUnitTest {
    private static final double INITIAL_BATTERY = 0.8;
    private static final double LOW_THRESHOLD = 0.2;
    private static final double MAX_CAPACITY = 1.0;
    private static final double MIN_CAPACITY = 0.0;

    private EnergySystem energySystem;

    @BeforeEach
    void setUp() {
        energySystem = EnergySystem.builder()
                .batteryLevel(INITIAL_BATTERY)
                .lowBatteryThreshold(LOW_THRESHOLD)
                .maxBattery(MAX_CAPACITY)
                .minBattery(MIN_CAPACITY)
                .build();
    }

    @Nested
    class PositiveTests {
        @Test
        @DisplayName("Проверка инициализации через Builder")
        void testBuilderInitialization() {
            assertEquals(INITIAL_BATTERY, energySystem.getBatteryLevel());
            assertEquals(LOW_THRESHOLD, energySystem.getLowBatteryThreshold());
            assertEquals(MAX_CAPACITY, energySystem.getMaxBattery());
        }

        @Test
        @DisplayName("Успешное потребление энергии")
        void testEnergyConsumption() {
            double consumption = 0.3;
            energySystem.consume(consumption);
            assertEquals(INITIAL_BATTERY - consumption, energySystem.getBatteryLevel(), 0.001);
        }

        @Test
        @DisplayName("Проверка достаточности энергии (выше порога)")
        void testHasSufficientPowerTrue() {
            assertTrue(energySystem.hasSufficientPower());
        }

        @Test
        @DisplayName("Потребление до границы минимального заряда")
        void testConsumeToExactlyMin() {
            energySystem.consume(INITIAL_BATTERY);
            assertEquals(MIN_CAPACITY, energySystem.getBatteryLevel());
        }
    }

    @Nested
    class NegativeTests {
        @Test
        @DisplayName("Проверка достаточности энергии (ниже порога)")
        void testHasSufficientPowerFalse() {
            energySystem.setBatteryLevel(0.15);
            assertFalse(energySystem.hasSufficientPower());
        }

        @Test
        @DisplayName("Потребление энергии сверх доступного лимита")
        void testConsumeMoreThanAvailable() {
            energySystem.consume(1.5);
            assertEquals(MIN_CAPACITY, energySystem.getBatteryLevel());
        }
    }
}
