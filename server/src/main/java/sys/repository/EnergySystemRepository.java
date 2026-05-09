package sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sys.domains.satellites.EnergySystem;

public interface EnergySystemRepository extends JpaRepository<EnergySystem, Long> {
}
