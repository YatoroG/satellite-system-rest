package sys.domains.satellites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Embeddable
public class SatelliteState {
    @JsonIgnore
    private boolean isActive = false;
    private String statusMessage;

    public SatelliteState() {
        this.statusMessage = "Не активирован";
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
}
