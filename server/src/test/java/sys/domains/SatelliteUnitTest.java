package sys.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sys.domains.satellites.EnergySystem;
import sys.domains.satellites.Satellite;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты функциональности сущности Satellite")
public class SatelliteUnitTest {
    private static final String SATELLITE_NAME = "testSatellite1";
    private static final double HIGH_BATTERY = 0.8;
    private static final double LOW_BATTERY = 0.1;

    private TestSatellite satellite;

    static class TestSatellite extends Satellite {
        public TestSatellite(String name, double batteryLevel) {
            super(name, batteryLevel);
        }
        @Override
        public void performMission() { }
    }

    @BeforeEach
    void setUp() {
        satellite = new TestSatellite(SATELLITE_NAME, HIGH_BATTERY);
    }

    @Nested
    class PositiveTests {
        @Test
        @DisplayName("Инициализация через конструктор")
        void testConstructorInitialization() {
            assertEquals(SATELLITE_NAME, satellite.getName());
            assertNotNull(satellite.getEnergy());
            assertNotNull(satellite.getState());
            assertEquals(HIGH_BATTERY, satellite.getEnergy().getBatteryLevel());
            assertFalse(satellite.getState().isActive());
        }

        @Test
        @DisplayName("Успешная активация при достаточном заряде")
        void testSuccessfulActivation() {
            boolean result = satellite.activate();
            assertTrue(result);
            assertTrue(satellite.getState().isActive());
        }

        @Test
        @DisplayName("Деактивация активного спутника")
        void testDeactivation() {
            satellite.activate();
            assertTrue(satellite.getState().isActive());
            satellite.deactivate();
            assertFalse(satellite.getState().isActive());
        }

        @Test
        @DisplayName("Потребление энергии энергосистемой")
        void testEnergyConsumption() {
            EnergySystem energy = satellite.getEnergy();
            double initialLevel = energy.getBatteryLevel();
            energy.consume(0.2);
            assertEquals(initialLevel - 0.2, energy.getBatteryLevel(), 0.001);
        }
    }

    @Nested
    class NegativeTests {
        @Test
        @DisplayName("Отказ в активации при низком заряде")
        void testActivationFailureLowBattery() {
            satellite.getEnergy().setBatteryLevel(LOW_BATTERY);
            boolean result = satellite.activate();
            assertFalse(result);
            assertFalse(satellite.getState().isActive());
        }

        @Test
        @DisplayName("Потребление энергии сверх лимита (минимум)")
        void testEnergyConsumptionBelowMin() {
            EnergySystem energy = satellite.getEnergy();
            energy.consume(2.0);
            assertEquals(energy.getMinBattery(), energy.getBatteryLevel());
        }

        @Test
        @DisplayName("Деактивация уже выключенного спутника")
        void testDeactivateAlreadyInactive() {
            assertFalse(satellite.getState().isActive());
            assertDoesNotThrow(() -> satellite.deactivate());
            assertFalse(satellite.getState().isActive());
        }
    }
}
