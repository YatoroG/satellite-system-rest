package sys.telemetry;

import javax.annotation.PostConstruct;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import sys.repository.SatelliteRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryClientService {
    @GrpcClient("telemetry-service")
    private TelemetryServiceGrpc.TelemetryServiceStub asyncStub;

    private final SatelliteRepository satelliteRepository;

    @PostConstruct
    public void startStreaming() {
        log.info("Запуск клиента телеметрии, подключение к telemetry-service:9092");
        TelemetryRequest request = TelemetryRequest.getDefaultInstance();

        StreamObserver<TelemetryUpdate> responseObserver = new StreamObserver<>() {

            @Override
            public void onNext(TelemetryUpdate value) {
                log.info("Телеметрия: спутник {}, temp_in={}, temp_out={}",
                        value.getSatelliteId(), value.getTemperatureInside(), value.getTemperatureOutside());
                satelliteRepository.findById(value.getSatelliteId()).ifPresent(satellite -> {
                    satellite.setTemperatureInside(value.getTemperatureInside());
                    satellite.setTemperatureOutside(value.getTemperatureOutside());
                    satelliteRepository.save(satellite);
                });

            }

            @Override
            public void onError(Throwable t) {
                log.error("Ошибка: телеметрия");
            }

            @Override
            public void onCompleted() {
                log.info("Завершено получение телеметрии");
            }
        };

        asyncStub.streamTelemetry(request, responseObserver);
    }
}
