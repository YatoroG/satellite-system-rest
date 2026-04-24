package sys.domains.satellites;

import lombok.Builder;
import lombok.Getter;

@Builder
public class EnergySystem {
    @Getter
    protected double batteryLevel;
    private double low_battery_threshold;
    private double max_battery;
    private double min_battery;

    public boolean consume(double batteryAmount) {
        if (batteryAmount <= 0 || batteryLevel <= min_battery) {
            return false;
        }
        batteryLevel = Math.max(min_battery, batteryLevel - batteryAmount);
        return true;
    }

    public boolean hasSufficientPower() {
        return batteryLevel > low_battery_threshold;
    }

    @Override
    public String toString() {
        return "EnergySystem{" +
                "batteryLevel=" + batteryLevel + '}';
    }
}
