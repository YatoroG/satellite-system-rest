package sys.factory.impl;

import org.springframework.stereotype.Component;
import sys.domains.satellites.ImagingSatellite;
import sys.domains.satellites.ImagingSatelliteParam;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.factory.SatelliteFactory;
import sys.utils.SatelliteType;
import sys.utils.SpaceOperationException;

@Component
public class ImagingSatelliteFactory implements SatelliteFactory {

    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (!(SatelliteType.IMAGE.equals(param.getType())
                && param instanceof ImagingSatelliteParam imagingSatelliteParam)) {
            throw new SpaceOperationException("Ошибка в параметре для спутника изображений");
        }

        return new ImagingSatellite(
                imagingSatelliteParam.getName(),
                imagingSatelliteParam.getBatteryLevel(),
                imagingSatelliteParam.getResolution()
        );
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return type == SatelliteType.IMAGE;
    }
}
