package sys.domains.satellites;

public class SatelliteState {
    private boolean isActive = false;
    private String statusMessage;

    public SatelliteState() {
        this.statusMessage = "Не активирован";
    }

    public boolean isActive() {
        return isActive;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public boolean activate(boolean hasSufficientPower) {
        if (hasSufficientPower && !isActive) {
            isActive = true;
            statusMessage = "Активен";
            return true;
        }
        statusMessage = hasSufficientPower ? "Уже активен" : "Недостаточно энергии";
        return false;
    }

    public void deactivate() {
        isActive = false;
        statusMessage = "Деактивирован";
    }

    @Override
    public String toString() {
        return "SatelliteState{" +
                "isActive=" + isActive +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
