public abstract class Satellite {
    protected String name;
    protected SatelliteState state;
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = new EnergySystem(Math.max(0.0, Math.min(1.0, batteryLevel)));
        this.state = new SatelliteState();
        notifyAboutSatelliteCreation();
    }

    public String getName() {
        return name;
    }

    public boolean activate() {
        if (energy.getBatteryLevel() > 0.2 && !state.isActive()) {
            state.activate();
        }
        notifyAboutActivation(state.isActive());
        return state.isActive();
    }

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
            System.out.println("Деактивирован спутник: " + name);
        }
    }

    public void checkBatteryLevel() {
        if (energy.getBatteryLevel() < 0.2 && state.isActive()) {
            System.out.println("\uD83D\uDD34 Низкий заряд: " + name + " деактивируется");
            state.deactivate();
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

    protected abstract void performMission();
}