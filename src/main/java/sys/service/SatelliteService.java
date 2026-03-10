package sys.service;

import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.utils.SpaceOperationException;

public interface SatelliteService {
    Satellite createSatellite(SatelliteParam param) throws SpaceOperationException;
}
