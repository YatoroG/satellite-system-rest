package sys.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.factory.SatelliteFactory;
import sys.utils.SpaceOperationException;

@Service
@RequiredArgsConstructor
public class SatelliteFactoryService {
    private final List<SatelliteFactory> factories;

    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        return factories.stream()
                .filter(factory -> factory.isSatelliteTypeSupported(param.getType()))
                .findFirst()
                .orElseThrow(() -> new SpaceOperationException("Тип спутника не поддерживается: " + param.getType()))
                .createSatelliteWithParameter(param);
    }
}
