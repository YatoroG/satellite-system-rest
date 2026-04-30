package sys.domains.satellites;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "satellite_constellation")
@Getter
@Setter
@NoArgsConstructor
public class SatelliteConstellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String constellationName;

    @JsonManagedReference
    @OneToMany(mappedBy = "constellation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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