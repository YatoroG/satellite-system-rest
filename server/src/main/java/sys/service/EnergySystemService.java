package sys.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.domains.requests.EnergySystemUpdateRequest;
import sys.domains.satellites.EnergySystem;
import sys.repository.EnergySystemRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EnergySystemService {
    private final EnergySystemRepository energySystemRepository;

    @Transactional(readOnly = true)
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EnergySystem> getEnergySystemById(Long id) {
        return energySystemRepository.findById(id);
    }

    public EnergySystem updateEnergySystem(Long id, EnergySystemUpdateRequest request) {
        EnergySystem energySystem = energySystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Энергосистема не найдена"));

        if (request.batteryLevel() != null) energySystem.setBatteryLevel(request.batteryLevel());
        if (request.lowBatteryThreshold() != null) energySystem.setLowBatteryThreshold(request.lowBatteryThreshold());
        if (request.maxBattery() != null) energySystem.setMaxBattery(request.maxBattery());
        if (request.minBattery() != null) energySystem.setMinBattery(request.minBattery());

        return energySystemRepository.save(energySystem);
    }
}
