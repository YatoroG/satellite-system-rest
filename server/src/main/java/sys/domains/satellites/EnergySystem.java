package sys.domains.satellites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "energy_system")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnergySystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battery_level", nullable = false)
    protected double batteryLevel;

    @Column(name = "low_battery_threshold", nullable = false)
    private double lowBatteryThreshold;

    @Column(name = "max_battery", nullable = false)
    private double maxBattery;

    @Column(name = "min_battery", nullable = false)
    private double minBattery;

    public void consume(double batteryAmount) {
        if (batteryAmount > 0 || batteryLevel > minBattery) {
            batteryLevel = Math.max(minBattery, batteryLevel - batteryAmount);
        }
    }

    public boolean hasSufficientPower() {
        return batteryLevel > lowBatteryThreshold;
    }

    @Override
    public String toString() {
        return "EnergySystem{" +
                "batteryLevel=" + batteryLevel + '}';
    }
}
