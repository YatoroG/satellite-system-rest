package sys.domains.requests;

public record EnergySystemUpdateRequest(Double batteryLevel,
                                        Double lowBatteryThreshold,
                                        Double maxBattery,
                                        Double minBattery) {
}
