package lk.ijse.gdse.automationservice.controller;

import lk.ijse.gdse.automationservice.dto.TelemetryData;
import lk.ijse.gdse.automationservice.entity.AutomationLog;
import lk.ijse.gdse.automationservice.repository.AutomationRepository;
import lk.ijse.gdse.automationservice.services.AutomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    @PostMapping("/process")
    public void processData(@RequestBody TelemetryData data) {
        automationService.processTelemetry(data);
    }

    @GetMapping("/logs")
    public List<AutomationLog> getAllLogs(@RequestHeader("loggedInUser") String userId) {
        return automationService.findAllLogs(userId);
    }
}
