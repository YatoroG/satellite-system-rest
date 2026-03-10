package sys.domains.satellites;

import sys.constants.EnergySystemConstants;

public abstract class Satellite {
    protected String name;
    protected SatelliteState state;
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .low_battery_threshold(EnergySystemConstants.LOW_BATTERY_THRESHOLD)
                .max_battery(EnergySystemConstants.MAX_BATTERY)
                .min_battery(EnergySystemConstants.MIN_BATTERY)
                .build();
        this.state = new SatelliteState();
        notifyAboutSatelliteCreation();
    }

    public String getName() {
        return name;
    }

    public SatelliteState getState() {
        return state;
    }

    public EnergySystem getEnergy() {
        return energy;
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
