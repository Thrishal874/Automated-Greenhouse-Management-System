package lk.ijse.gdse.sensorservice.client;

import lk.ijse.gdse.sensorservice.dto.TelemetryData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-service")
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    void sendToAutomation(@RequestBody TelemetryData data);
}