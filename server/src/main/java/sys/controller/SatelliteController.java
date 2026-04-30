package sys.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys.domains.satellites.Satellite;
import sys.domains.satellites.SatelliteParam;
import sys.service.SatelliteService;
import sys.utils.SpaceOperationException;

@RestController
@RequestMapping("/api/satellites")
@RequiredArgsConstructor
public class SatelliteController {
    private final SatelliteService satelliteService;

    @PostMapping
    public ResponseEntity<Satellite> createSatellite(@RequestBody SatelliteParam param) throws SpaceOperationException {
        Satellite created = satelliteService.createSatellite(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Satellite> getAllSatellites() {
        return satelliteService.getAllSatellites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatelliteById(@PathVariable Long id) {
        return satelliteService.getSatelliteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Satellite> updateSatellite(@PathVariable Long id, @RequestBody Satellite satellite) {
        Satellite updatedSatellite = satelliteService.updateSatellite(id, satellite);
        return ResponseEntity.ok(updatedSatellite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatellite(@PathVariable Long id) {
        satelliteService.deleteSatellite(id);
        return ResponseEntity.noContent().build();
    }
}
