package sys.domains.satellites;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class SatelliteConstellation {
    @Getter
    private final String constellationName;
    private final List<Satellite> satellites = new ArrayList<>();

    public SatelliteConstellation(String constellationName) {
        this.constellationName = constellationName;
    }

    public void addSatellite(Satellite satellite) {
        if (satellite != null && !satellites.contains(satellite)) {
            satellites.add(satellite);
        }
    }

    public void executeAllMissions() {
        for (Satellite satellite : satellites) {
            satellite.performMission();
        }
    }

    public List<Satellite> getSatellites() {
        return new ArrayList<>(satellites);
    }

    public Satellite getSatelliteFromConstellation(String satelliteName) {
        return satellites.stream().filter(s -> s.getName().equals(satelliteName)).findFirst().orElse(null);
    }

    public void deleteSatellite(Satellite satellite) {
        if (satellite != null) {
            satellites.remove(satellite);
        }
    }
}