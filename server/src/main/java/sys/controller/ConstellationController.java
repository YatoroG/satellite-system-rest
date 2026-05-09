package sys.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys.domains.satellites.SatelliteConstellation;
import sys.service.ConstellationService;

@RestController
@RequestMapping("/api/constellations")
@RequiredArgsConstructor
public class ConstellationController {
    private final ConstellationService constellationService;

    public record CreateConstellationRequest(String name) {}
    public record AddSatelliteRequest(Long satelliteId) {}

    @PostMapping
    public ResponseEntity<SatelliteConstellation> createConstellation(@RequestBody CreateConstellationRequest request) {
        SatelliteConstellation created = constellationService.createAndSaveConstellation(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationService.getAllConstellations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteConstellation> getConstellationById(@PathVariable Long id) {
        return constellationService.getConstellationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstellation(@PathVariable Long id) {
        constellationService.deleteConstellation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{constellationId}/satellites")
    public ResponseEntity<Void> addSatelliteToConstellation(@PathVariable Long id, @RequestBody AddSatelliteRequest request) {
        constellationService.addSatelliteToConstellation(id, request.satelliteId);
        return ResponseEntity.ok().build();
    }
}
