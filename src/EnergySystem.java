public class EnergySystem {
    protected double batteryLevel;

    public EnergySystem(double batteryLevel) {
        this.batteryLevel = Math.max(0.0, Math.min(1.0, batteryLevel));
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void consume(double batteryAmount) {
        if (batteryAmount > 0) {
            batteryLevel = Math.max(0.0, batteryLevel - batteryAmount);
        }
    }
}
