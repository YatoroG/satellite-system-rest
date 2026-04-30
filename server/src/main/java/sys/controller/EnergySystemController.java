package sys.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys.domains.requests.EnergySystemUpdateRequest;
import sys.domains.satellites.EnergySystem;
import sys.service.EnergySystemService;

@RestController
@RequestMapping("/api/energy-systems")
@RequiredArgsConstructor
public class EnergySystemController {
    private final EnergySystemService energySystemService;

    @GetMapping
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemService.getAllEnergySystems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergySystem> getEnergySystemById(@PathVariable Long id) {
        return energySystemService.getEnergySystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnergySystem> updateEnergySystem(@PathVariable Long id, @RequestBody EnergySystemUpdateRequest request) {
        EnergySystem updatedEnergySystem = energySystemService.updateEnergySystem(id, request);
        return ResponseEntity.ok(updatedEnergySystem);
    }
}
