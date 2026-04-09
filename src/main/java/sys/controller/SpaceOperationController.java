package sys.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys.domains.requests.AddSatelliteRequest;
import sys.domains.requests.MissionRequest;
import sys.service.SpaceOperationCenterService;
import sys.utils.SpaceOperationException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpaceOperationController {
    private final SpaceOperationCenterService spaceOperationCenterService;

    @PostMapping("/missions")
    public ResponseEntity<Void> executeMission(@RequestBody MissionRequest request) {
        spaceOperationCenterService.executeMission(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-satellites")
    public ResponseEntity<Void> addSatellites(@RequestBody AddSatelliteRequest request) throws SpaceOperationException {
        spaceOperationCenterService.addSatellite(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/overview")
    public ResponseEntity<String> overview() {
        String overview = spaceOperationCenterService.getSystemOverview();
        return ResponseEntity.ok(overview);
    }

    @DeleteMapping("/{constellationName}/satellites/{satelliteName}")
    public ResponseEntity<Void> deleteSatellite(String constellationName, String satelliteName) {
        spaceOperationCenterService.deleteSatellite(constellationName, satelliteName);
        return ResponseEntity.ok().build();
    }
}
