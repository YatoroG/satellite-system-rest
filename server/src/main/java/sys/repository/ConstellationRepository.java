package sys.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import sys.domains.satellites.SatelliteConstellation;

@Service
public class ConstellationRepository {
    private final Map<String, SatelliteConstellation> constellations = new HashMap<>();

    public SatelliteConstellation getConstellation(String name) {
        SatelliteConstellation constellation = constellations.get(name);
        if (constellation == null) {
            throw new RuntimeException("Группировка с именем " + name + " не найдена");
        }
        return constellations.get(name);
    }

    public Map<String, SatelliteConstellation> getAllConstellations() {
        return constellations;
    }

    public boolean containsConstellation(String name) {
        return constellations.containsKey(name);
    }

    public void addConstellation(SatelliteConstellation constellation) {
        constellations.put(constellation.getConstellationName(), constellation);
        System.out.println("Добавлена группировка: " + constellation.getConstellationName());
    }

    public boolean removeConstellation(String name) {
        if (!constellations.containsKey(name)) {
            System.out.println("Группировка с именем " + name + " не существует");
            return false;
        }
        constellations.remove(name);
        System.out.println("Удалена группировка: " + name);
        return true;
    }
}
