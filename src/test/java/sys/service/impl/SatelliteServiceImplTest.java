package sys.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sys.domains.satellites.*;
import sys.service.SatelliteService;
import sys.utils.SpaceOperationException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Юнит-тесты функциональности сервиса создания спутников")
public class SatelliteServiceImplTest {
    private static final String SATELLITE_NAME = "testSatellite";
    private static final double BATTERY_LEVEL = 0.8;
    private static final double BANDWIDTH = 300.0;
    private static final double RESOLUTION = 15.0;

    @Autowired
    private SatelliteService satelliteService;

    @Test
    @DisplayName("Создание спутника изображений с параметром")
    void imgServiceWithParams() throws SpaceOperationException {
        SatelliteParam imagingParam = new ImagingSatelliteParam(SATELLITE_NAME, BATTERY_LEVEL, RESOLUTION);

        Satellite satellite = satelliteService.createSatellite(imagingParam);

        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        ImagingSatellite imagingSatellite = (ImagingSatellite) satellite;
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
        assertEquals(RESOLUTION, ((ImagingSatellite) satellite).getResolution());
    }

    @Test
    @DisplayName("Создание спутника связи с параметром")
    void commServiceWithParams() throws SpaceOperationException {
        SatelliteParam commParam = new CommunicationSatelliteParam(SATELLITE_NAME, BATTERY_LEVEL, BANDWIDTH);

        Satellite satellite = satelliteService.createSatellite(commParam);

        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        CommunicationSatellite communicationSatellite = (CommunicationSatellite) satellite;
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
        assertEquals(BANDWIDTH, ((CommunicationSatellite) satellite).getBandwidth());
    }
}
