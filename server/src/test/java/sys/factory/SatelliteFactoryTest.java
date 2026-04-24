package sys.factory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sys.domains.satellites.CommunicationSatellite;
import sys.domains.satellites.CommunicationSatelliteParam;
import sys.domains.satellites.ImagingSatellite;
import sys.domains.satellites.ImagingSatelliteParam;
import sys.factory.impl.CommunicationSatelliteFactory;
import sys.factory.impl.ImagingSatelliteFactory;
import sys.utils.SpaceOperationException;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Юнит-тесты функциональности фабрики создания спутников")
public class SatelliteFactoryTest {
    private static final String SATELLITE_NAME = "testSatellite";
    private static final double BATTERY_LEVEL = 0.8;
    private static final double BANDWIDTH = 300.0;
    private static final double RESOLUTION = 15.0;

    private static SatelliteFactory communicationSatelliteFactory;
    private static SatelliteFactory imagingSatelliteFactory;

    @BeforeAll
    static void setUp() {
        communicationSatelliteFactory = new CommunicationSatelliteFactory();
        imagingSatelliteFactory = new ImagingSatelliteFactory();
    }

    @Test
    @DisplayName("Создание спутника связи с параметром")
    void commFactoryWithParams() throws SpaceOperationException {
        CommunicationSatelliteParam params = new CommunicationSatelliteParam(
                SATELLITE_NAME,
                BATTERY_LEVEL,
                BANDWIDTH
        );

        CommunicationSatellite satellite = (CommunicationSatellite) communicationSatelliteFactory
                .createSatelliteWithParameter(params);

        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
        assertEquals(BANDWIDTH, satellite.getBandwidth());
    }

    @Test
    @DisplayName("Создание спутника изображений с параметром")
    void imgFactoryWithParams() throws SpaceOperationException {
        ImagingSatelliteParam params = new ImagingSatelliteParam(
                SATELLITE_NAME,
                BATTERY_LEVEL,
                RESOLUTION
        );

        ImagingSatellite satellite = (ImagingSatellite) imagingSatelliteFactory
                .createSatelliteWithParameter(params);

        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
        assertEquals(RESOLUTION, satellite.getResolution());
    }
}
