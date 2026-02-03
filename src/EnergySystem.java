public class EnergySystem {
    protected double batteryLevel;

    public EnergySystem(double batteryLevel) {
        this.batteryLevel = batteryLevel;
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
