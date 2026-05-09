package sys.domains.satellites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@DiscriminatorValue("COMMUNICATION")
@NoArgsConstructor
public class CommunicationSatellite extends Satellite {
    private double bandwidth;

    public CommunicationSatellite(String name, double batteryLevel, double bandwidth) {
        super(name, batteryLevel);
        this.bandwidth = bandwidth;
    }

    @Override
    public void performMission() {
        if (state.isActive()) {
            System.out.println(name + ": Передача данных со скоростью " + bandwidth + " Мбит/с");
            sendData(bandwidth);
            energy.consume(0.05);
        } else {
            System.out.println("\uD83D\uDD34 " + name + ": Не может выполнить передачу данных - не активен");
        }
    }

    private void sendData(double data) {
        if (state.isActive()) {
            System.out.println(name + ": Отправил " + bandwidth + " Мбит данных!");
        }
    }
}
