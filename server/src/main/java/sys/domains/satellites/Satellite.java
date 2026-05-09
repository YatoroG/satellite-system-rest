package sys.domains.satellites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sys.constants.EnergySystemConstants;

@Entity
@Table(name = "satellite")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "satellite_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constellation_id")
    protected SatelliteConstellation constellation;

    @Embedded
    protected SatelliteState state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "energy_id", unique = true)
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .lowBatteryThreshold(EnergySystemConstants.LOW_BATTERY_THRESHOLD)
                .maxBattery(EnergySystemConstants.MAX_BATTERY)
                .minBattery(EnergySystemConstants.MIN_BATTERY)
                .build();
        this.state = new SatelliteState();
        notifyAboutSatelliteCreation();
    }

    public boolean activate() {
        notifyAboutActivation(state.activate(energy.hasSufficientPower()));
        return state.isActive();
    }

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
            System.out.println("Деактивирован спутник: " + name);
        }
    }

    public void notifyAboutSatelliteCreation() {
        System.out.println("Создан спутник: " + name + " (заряд: " + (int) (energy.getBatteryLevel() * 100) + "%)");
    }

    public void notifyAboutActivation(boolean isActive) {
        if (isActive) {
            System.out.println("\uD83D\uDFE2 " + name + ": Активация успешна");
        } else {
            System.out.println("\uD83D\uDD34 " + name + ": Ошибка активации (заряд " + (int) (energy.getBatteryLevel() * 100) + "%)");
        }
    }

    public abstract void performMission();
}
