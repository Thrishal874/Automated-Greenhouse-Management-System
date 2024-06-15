package lk.ijse.gdse.automationservice.services;

import lk.ijse.gdse.automationservice.dto.TelemetryData;
import lk.ijse.gdse.automationservice.entity.AutomationLog;

import java.util.List;

public interface AutomationService {
    void processTelemetry(TelemetryData data);

    List<AutomationLog> findAllLogs(String userId);
}
