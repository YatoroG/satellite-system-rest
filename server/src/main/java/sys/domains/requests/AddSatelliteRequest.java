package sys.domains.requests;

import java.util.List;
import sys.domains.satellites.SatelliteParam;

public record AddSatelliteRequest(String constellationName, List<SatelliteParam> satelliteParams) {
}
