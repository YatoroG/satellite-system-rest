package sys.services;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import sys.telemetry.TelemetryRequest;
import sys.telemetry.TelemetryServiceGrpc;
import sys.telemetry.TelemetryUpdate;

@GrpcService
public class TelemetryServiceGrpcImpl extends TelemetryServiceGrpc.TelemetryServiceImplBase {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();

    private static final List<Long> SATELLITE_IDS = List.of(1L, 2L, 3L);

    @Override
    public void streamTelemetry(TelemetryRequest request, StreamObserver<TelemetryUpdate> responseObserver) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                Long satId = SATELLITE_IDS.get(random.nextInt(SATELLITE_IDS.size()));
                TelemetryUpdate update = TelemetryUpdate.newBuilder()
                        .setSatelliteId(satId)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
                responseObserver.onNext(update);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
