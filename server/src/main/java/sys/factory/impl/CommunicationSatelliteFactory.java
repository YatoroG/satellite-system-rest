package sys.factory.impl;

import org.springframework.stereotype.Component;
import sys.domains.satellites.CommunicationSatellite;
import sys.domains.satellites.CommunicationSatelliteParam;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.factory.SatelliteFactory;
import sys.utils.SatelliteType;
import sys.utils.SpaceOperationException;

@Component
public class CommunicationSatelliteFactory implements SatelliteFactory {

    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (!(SatelliteType.COMMUNICATION.equals(param.getType())
                && param instanceof CommunicationSatelliteParam communicationSatelliteParam)) {
            throw new SpaceOperationException("Ошибка в параметре для спутника связи");
        }

        return new CommunicationSatellite(
                communicationSatelliteParam.getName(),
                communicationSatelliteParam.getBatteryLevel(),
                communicationSatelliteParam.getBandwidth()
        );
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return type == SatelliteType.COMMUNICATION;
    }
}
