package sys.configurations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import sys.domains.requests.AddSatelliteRequest;
import sys.domains.requests.MissionRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceOperationClient {
    private final RestClient restClient;

    public void executeMission(MissionRequest request) {
        restClient.post()
                .uri("/missions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
        log.info("Миссия выполнена: {}", request);
    }

    public void addSatellite(AddSatelliteRequest request) {
        restClient.post()
                .uri("/add-satellites")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
        log.info("Спутник добавлен в группировку: {}", request.constellationName());
    }

    public String getOverview() {
        return restClient.get()
                .uri("/overview")
                .retrieve()
                .body(String.class);
    }

    public void deleteSatellite(String constellationName, String satelliteName) {
        restClient.delete()
                .uri("/constellations/{constellationName}/satellites/{satelliteName}",
                        constellationName, satelliteName)
                .retrieve()
                .toBodilessEntity();
        log.info("Спутник {} удален из группировки {}", satelliteName, constellationName);
    }
}
