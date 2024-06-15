package lk.ijse.gdse.automationservice.services.impl;

import lk.ijse.gdse.automationservice.client.ZoneClient;
import lk.ijse.gdse.automationservice.dto.TelemetryData;
import lk.ijse.gdse.automationservice.dto.ZoneDTO;
import lk.ijse.gdse.automationservice.entity.AutomationLog;
import lk.ijse.gdse.automationservice.repository.AutomationRepository;
import lk.ijse.gdse.automationservice.services.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AutomationServiceImpl implements AutomationService {

    @Autowired
    private AutomationRepository repository;

    @Autowired
    private ZoneClient zoneClient;

    @Override
    public void processTelemetry(TelemetryData data) {

        try {

            Map<String, Object> response = zoneClient.getZoneById(data.getZoneId());

            Map<String, Object> zoneData = (Map<String, Object>) response.get("data");

            if (zoneData != null) {

                String userId = (String) zoneData.get("userId");
                double maxTemp = Double.parseDouble(zoneData.get("maxTemp").toString());
                double minTemp = Double.parseDouble(zoneData.get("minTemp").toString());

                double currentTemp = data.getValue().getTemperature();
                String action = null;

                if (currentTemp > maxTemp) {
                    action = "TURN_FAN_ON";
                } else if (currentTemp < minTemp) {
                    action = "TURN_HEATER_ON";
                }

                if (action != null) {
                    AutomationLog log = new AutomationLog();
                    log.setZoneId(data.getZoneId());
                    log.setUserId(userId);
                    log.setCurrentTemp(currentTemp);
                    log.setAction(action);
                    log.setTimestamp(LocalDateTime.now());

                    repository.save(log);
                    System.out.println("Rule Triggered: " + action + " for Zone " + data.getZoneId());
                }
            }

        } catch (Exception e) {
            System.err.println("Error processing telemetry: " + e.getMessage());
        }

    }

    @Override
    public List<AutomationLog> findAllLogs(String userId) {
        return repository.findByUserId(userId);
    }

}
