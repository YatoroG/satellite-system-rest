package sys.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sys.domains.satellites.SatelliteConstellation;

public interface ConstellationRepository extends JpaRepository<SatelliteConstellation, Long> {
    Optional<SatelliteConstellation> findByConstellationName(String name);
    boolean existsByConstellationName(String name);
    void deleteByConstellationName(String name);
}
